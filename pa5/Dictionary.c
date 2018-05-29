// Aaron Chan
// pa5
// Dictionary.c
// 5/28/16

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<assert.h>
#include"Dictionary.h"

const int tableSize = 101;

// NodeObj
typedef struct NodeObj{
  char* key;
  char* value;
  struct NodeObj* next;
} NodeObj;

// Node
typedef NodeObj* Node;

// newNode()
// constructor of  Node type
Node newNode(char* k, char* v){
  Node N = malloc(sizeof(NodeObj));
  assert(N != NULL);
  N->key = k;
  N->value = v;
  N->next = NULL;
  return(N);
}

// freeNode()
// destructor for Node type
void freeNode(Node* pN){
  if(pN != NULL && *pN != NULL ){
    free(*pN);
    *pN = NULL;
  }
}

// DictionaryObject
typedef struct DictionaryObj{
  Node *table;
  int numItems;
}
  DictionaryObj;
  
// newDictionary() 
// Constructor for Dictionary Type
Dictionary newDictionary() {
  Dictionary D = malloc(sizeof(Dictionary));
  assert(D != NULL);
  D->table = calloc(tableSize,sizeof(Node));
  assert(D->table != NULL);
  D->numItems = 0;
  return D;
}

// freeDictionary() 
// destructor for Dictionary type
void freeDictionary(Dictionary* pD){
  if(pD != NULL && *pD != NULL){
    makeEmpty(*pD);
    free((*pD)->table);
    free(*pD);
    *pD = NULL;
  }
}

// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
  int sizeInBits = 8*sizeof(unsigned int);
  shift = shift & (sizeInBits - 1);
  if ( shift == 0 )
    return value;
  return (value << shift) | (value >> (sizeInBits - shift));
}  

// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) {
  unsigned int result = 0xBAE86554;
  while (*input) {
    result ^= *input++;
    result = rotate_left(result, 5);
  }  
  return result;
}

// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(char* key){
  return pre_hash(key)%tableSize;
}

// isEmpty()
// returns 1 (true) if S is empty, 0 if not
int isEmpty(Dictionary D){
  if( D==NULL){
    fprintf(stderr, "Dictionary Error: calling isEmpty() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  if(D->numItems>0){
    return 0;
  }
  return 1;
}

// size()
// returns the number of(key,value) pairs in D
int size(Dictionary D){
  return D->numItems;
}

// findKey()
// finds key
Node findKey(Dictionary D, char* k){
  Node N;
  N = D->table[hash(k)];
  while(N != NULL){
    if(strcmp(N->key,k)== 0)
      break;
    N = N->next;
  }
  return N;
}

// lookup()
// returns the value v suc that (k, v) is in D, or returns NULL if no such value exists
char* lookup(Dictionary D, char* k){
  if( D == NULL ){
    fprintf(stderr, "Dictionary Error: calling lookup() on NULL Dictionary\n");
    exit(EXIT_FAILURE);
  }
  if(D->numItems == 0){
    fprintf(stderr, "Dictionary Error: calling lookup() on empty Dictionary reference");
    exit(EXIT_FAILURE);
  }
  if(findKey(D,k) == NULL)
    return NULL;
  else
    return findKey(D,k)->value;
}


// insert()
// inserts new(k,v) pair into D
void insert(Dictionary D, char* k, char* v){
  Node N;
  int i = hash(k);
  if(D == NULL){
    fprintf(stderr, "Dictionary Error: calling insert on NULL Dictionary\n");
    exit(EXIT_FAILURE);
  }
  if( findKey(D, k) != NULL){
    fprintf(stderr, "Dictionary Error: calling insert() on a pre-existing key");
    exit(EXIT_FAILURE);
  } 
  if(D->table[i] == NULL){
    D->table[i]  = newNode(k, v);
    D->numItems++; 
  }
  else{
    N = newNode(k, v);
    N->next = D->table[i];
    D->table[i] = N;
    D->numItems++;
  }
}


// MakeEmpty()
// reset D to the empty state
void makeEmpty(Dictionary D){
  for(int i = 0; i<tableSize; i++){
    while(D->table[i] != NULL){
      Node N;
      N = D-> table[i];
      D->table[i]=N->next;
      freeNode(&N);
      N = NULL;
    }
  }
  D->numItems = 0; 
     
}

// delete()
// deletes pair with key k
void delete(Dictionary D, char* k){
  Node N;
  Node A;
  if( findKey(D,k) == NULL ){
    fprintf(stderr, "errror: key not found\n");
    exit(EXIT_FAILURE);
  }
  N = findKey(D,k);
  if(N == D->table[hash(k)] && N->next == NULL){
    N = NULL;
    freeNode(&N);
  }else if(N == D->table[hash(k)]){
    D->table[hash(k)] = N->next;
    N = NULL;
    freeNode(&N);
  }else{
    A = D->table[hash(k)];
    while(A->next != N){
      A = A->next;
    }
    A->next = N->next;
    freeNode(&N);
            
  }
  D->numItems--;
}
 
// printDictionary()
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D){
  Node N;
  if( D == NULL ){
    fprintf(stderr, "Dictionary Error: calling printDictionary() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  for(int i = 0; i < tableSize; i++){
    N = D->table[i];
    while(N != NULL){
      fprintf(out, "%s %s \n" , N->key, N->value);
      N = N->next;
    }
  } 
} 

