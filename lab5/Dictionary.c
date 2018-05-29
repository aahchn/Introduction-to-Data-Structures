// Aaron Chan - aahchan
// 5/13/16
// Dictionary.c
// lab5

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<assert.h>
#include"Dictionary.h"

  //NodeObj
  typedef struct NodeObj{
    char* key;
    char* value;
    struct NodeObj* next;
  } NodeObj;

//Node
typedef NodeObj* Node;

//newNode()
//constructor of the Node type
Node newNode(char* k, char* v){
  Node N = malloc(sizeof(NodeObj));
  assert(N!=NULL);
  N->key = k;
  N->value = v;
  N->next = NULL;
  return(N);
}

//freeNode()
//destructor for the Node type

void freeNode(Node* pN){
  if(pN!=NULL && *pN!=NULL ){
    free(*pN);
    *pN = NULL;
  }
}

//DictinoaryObj
typedef struct DictionaryObj{
  Node head;
  Node tail;
  int numItems;
} DictionaryObj;

//----------------------------------------------------------------
//newDictionary()
//constructor for Dictionary type
Dictionary newDictionary(void){
  Dictionary S = malloc(sizeof(DictionaryObj));
  assert(S != NULL);
  S->head = NULL;
  S->tail = NULL;
  S->numItems = 0;
  return S;
}

//freeDictionary()
//destructor for the Dictionary type
void freeDictionary(Dictionary* pS){
  if( pS!=NULL && *pS!=NULL){
    if( !isEmpty(*pS) )makeEmpty(*pS);
    free(*pS);
    *pS = NULL;
  }
}
   

//isEmpty()
//returns 1 (true) if S is empty, 0 (false) otherwise
//pre: none
int isEmpty(Dictionary S){
  if( S==NULL ){
    fprintf(stderr, "Dictionary Error: calling isEmpty() on NULL Dictionary reference\n");      exit(EXIT_FAILURE);
  }
  if(S->numItems>0){
    return 0;
  }
  return 1;
}

//size()
//returns the number of(key,value) pairs in S
//pre: none
int size(Dictionary S){
  return S->numItems;
}


//lookup()
//returns the value v suc that (k, v) is in S, or returns NULL if no such value exists
//pre: none
char* lookup(Dictionary S, char* k){
  Node N = S->head;
  if( S == NULL ){
    fprintf(stderr, "Dictionary Error: calling lookup() on NULL Dictionary\n");
    exit(EXIT_FAILURE);
  }
  while(N != NULL){
    if(strcmp(N->key,k)== 0)
      return N->value;
    N = N->next;
  }
  return NULL;;
}

//insert()
//inserts new(k,v) pair into S
//pre: lookup(S,K)!= NULL
void insert(Dictionary S, char* k, char* v){
  if(S->numItems == 0){
    S->head = S->tail = newNode(k, v);
  }else{
    Node N;
    N = newNode(k, v);
    S->tail->next = N;
    S->tail = N;
  }
  S->numItems++;
} 

//makeEmpty()
//reset S to the empty state
//pre: none
void makeEmpty(Dictionary S){
  S->numItems = 0;
  freeNode(&S->head);
}

// delete()
// deletes pair with the key k
// pre: lookup(S, k)!=NULL
void delete(Dictionary S, char* k){
  Node N = S->head;
  if( lookup(S,k) == NULL ){
    fprintf(stderr, "error: key not found\n");
    exit(EXIT_FAILURE);
  }
  if(strcmp(N->key,k)==0){
    Node P = S->head;
    Node G = P->next;
    S->head = G;
    freeNode(&P);
  }else{
    while(N !=NULL && N->next != NULL){
      if(strcmp(N->next->key, k)==0){
	Node G = N;
	Node C = N->next;
	G->next = C->next;
	N=G;
	freeNode(&C);
      }
      N = N->next;
    }
  }
  S->numItems--;
}



//printDictionary()
//pre: none
//prints a text representation of S to the file pointed to by out
void printDictionary(FILE* out, Dictionary S){
  Node N;
  if( S==NULL ){
    fprintf(stderr, "Dictionary Error: calling printDictionary() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  for(N=S->head; N!=NULL; N=N->next) fprintf(out, "%s %s \n" , N->key, N->value);
  fprintf(out, "\n");
}
