// Aaron Chan
// PA2 - List.c
// Jan 28, 2017

#include<stdio.h>
#include<stdlib.h>
#include "List.h"

typedef struct NodeObj{  //creates the Node Object with data fields
  int data;
  struct NodeObj* next;
  struct NodeObj* prev;
} NodeObj;

typedef NodeObj* Node;

typedef struct ListObj{ //creates the List Object with data fields
  Node front;
  Node back;
  Node current;
  int length;
  int curindex;
} ListObj;

Node newNode(int data) {
	Node N = malloc(sizeof(NodeObj));
	N->data = data;
	N->next = NULL;
	N->prev = NULL;
	return(N);
}

void freeNode(Node* pN){  //frees Node memory
  if (pN != NULL && *pN != NULL){
    free(*pN);
    *pN = NULL;
  }
}

//Constructor-Destructors --------------------------------------
List newList(void){   //creates a List with these data fields
  List L;
  L = malloc(sizeof(ListObj));
  L->front = NULL;
  L->back = NULL;
  L->current = NULL;
  L->length = 0;
  L->curindex = -1;
  return(L);
}


void freeList(List* pL){  //frees the list
  if(pL != NULL && *pL != NULL) {
		while(length(*pL) > 0) {
			deleteBack(*pL);
		}
		free(*pL);
		*pL = NULL;
	}
}

//Access functions ---------------------------------------------

int length(List L){   //returns the number of elements in list
  if (L != NULL)
    return L->length;
  else{
    printf("List Error: called length() on NULL list ref\n");
    exit(1);
  }
}

int index(List L){  //returns index of current elements
  if(L != NULL)
     return L->curindex;
  else{
    printf("List Error: called index() on NULL list ref\n");
    exit(1);
  }
}

int front(List L){   //returns front element
  if (L->length <= 0){
    printf("List Error: called front() on empty list\n");
    exit(1);
  }
  else if (L == NULL){
    printf("List Error: called front() on NULL list\n");
  }
  else{
    return L->front->data;
}
}
int back(List L){   //returns back element
  if (L->length <= 0){
    printf("List Error: called back() on empty list\n");
    exit(1);
  }
  else if(L == NULL){
    printf("List Error: called back() on NULL list\n");
  }
  else{
   return L->back->data;
}
}
int get(List L){    //returns current element
  if (L->length <= 0){
    printf("List Error: called get() on empty list\n");
    exit(1);
  }
  else if(L == NULL){
    printf("List Error: called get() on NULL list\n");
    exit(1);
  }
  else if(L->curindex < 0){
    printf("List Error: called get() with undef index on List\n ");
    exit(1);
  }
  else
    return L->current->data;
}

int equals(List A, List B){  //returns true if list and L have same int sequence
  if(A->length != B->length)
    return 0;      //does nothing if Lists aren't equal
  if(A == NULL || B == NULL){
    printf("List Error: calling equals() on NULL list ref\n");
    exit(1);      //if lists are null, then error
  }
  Node temp = A->front;
  Node cfront = B->front;
  while(cfront->next != NULL && temp->next != NULL){
    if(cfront->data != temp->data){
      return 0;
    }
    cfront = cfront->next;
    temp = temp->next;
  }
  return 1;
}

//Manipulation functions --------------------------------------

void clear(List L){    //resets list to original empty state
  if (L == NULL){
    printf("List Error: calling clear() on NULL list ref\n");
    exit(1);
  }
	freeList(&L);
	L = newList();
}

void moveFront(List L){ //places cursor under front element if list is non-empty
  if(L == NULL){
    printf("List Error: calling moveFront() on NULL list ref\n");
    exit(1);
  }
  if(L->length >= 1){
    L->current = L->front;
    L->curindex = 0;
  }
}

void moveBack(List L){ //places cursor under back element if list is non-empty
  if (L == NULL){
    printf("List Error: calling moveBack() on NULL list ref\n");
    exit(1);
  }
  if(L->length >= 1){
    L->current = L->back;
    L->curindex = L->length - 1;
  }
}

void movePrev(List L){  //move cursor 1 step toward front of list
  if(L == NULL){
    printf("List Error: calling movePrev() on NULL list ref\n");
    exit(1);
  }
  if(L->curindex != 0 && L->current != NULL){
    L->current = L->current->prev;
    --L->curindex;
  }
  else if (L->current != NULL && L->curindex == 0){
    L->current = NULL;
    L->curindex = -1;
  }
}

void moveNext(List L){ //move cursor 1 step toward back of list
  if(L == NULL){
    printf("List Error: calling moveNext() on NULL list ref\n");
    exit(1);
  }
  if(L->curindex != L->length-1 && L->current != NULL){
    L->current = L->current->next;
    ++L->curindex;
  }
  else if(L->current != NULL && L->curindex == L->length-1){
    L->current = NULL;
    L->curindex = -1;
  }
}

void prepend(List L, int data){ //insert new element to front of list
  if(L == NULL){
    printf("List Error: calling prepend() on NULL list ref\n");
    exit(1);
  }
  Node temp = newNode(data);
  temp->next = L->front;
  if(L->front == NULL)
     L->back = temp;
  else
     L->front->prev = temp;
  L->front = temp;
  ++L->length;
}

void append(List L, int data){ //insert new element to end of list
  if(L == NULL){
    printf("List Error: calling append() on NULL list ref\n");
    exit(1);
  }
  Node temp = newNode(data);
  temp->prev = L->back;
  if(L->front == NULL)
     L->front = temp;
  else
     L->back->next = temp;
  L->back = temp;
  L->length++;
}

void insertBefore(List L, int data){ //insert new element before cursor
  if(L == NULL){
    printf("List Error: calling insertBefore() on NULL list ref\n");
    exit(1);
  }
  if(L->curindex < 0) {
   printf("List Error: insertBefore() called with an undef index on List\n");
   exit(1);
 }
  if(L->length < 1) {
   printf("List Error: insertBefore() called on an empty List\n");
   exit(1);
 }
 Node temp = newNode(data);
 temp->prev = L->current->prev;
 temp->next = L->current;
 if(L->current->prev != NULL)
    L->current->prev->next = temp;
 else
    L->front = temp;
 L->current->prev = temp;
 ++L->length;
}


void insertAfter(List L, int data){ //insert new element after cursor
  if(L == NULL){
    printf("List Error: calling insertAfter() on NULL list ref\n");
    exit(1);
  }
  if(L->curindex < 0) {
   printf("List Error: insertAfter() called with an undef index on List\n");
   exit(1);
 }
  if(L->length < 1) {
   printf("List Error: insertAfter() called on an empty List\n");
   exit(1);
 }
 Node temp = newNode(data);
 temp->prev = L->current;
 temp->next = L->current->next;
 if(L->current->next != NULL)
    L->current->next->prev = temp;
 else
    L->back = temp;
 L->current->next = temp;
 ++L->length;
}

void deleteFront(List L){ //delete front element
  if(L == NULL){
    printf("List Error: calling deleteFront() on NULL list ref\n");
    exit(1);
  }
  if(L->length < 1){
     printf("List Error: calling deleteFront() on empty list\n");
     exit(1);
   }
   if(L->current == L->front){
     L->current = NULL;
     L->curindex = -1;
   }
   Node temp = L->front;
   L->front = L->front->next;
   L->front->prev = NULL;
   --L->length;
   freeNode(&temp);
}

void deleteBack(List L){ //delete back element
  if(L == NULL){
    printf("List Error: calling deleteBack() on NULL list ref\n");
    exit(1);
  }
  if(L->length < 1){
     printf("List Error: calling deleteBack() on empty list\n");
     exit(1);
   }
   if(L->current == L->back){
     L->current = NULL;
     --L->curindex;
   }
   Node temp = L->back;
   L->back = L->back->prev;
   L->back->next = NULL;
   L->length--;
   freeNode(&temp);
}

void delete(List L){ //delete current element
  if(L == NULL) {
     printf("List Error: delete() called on NULL List reference\n");
     exit(1);
  }
  if(L->length < 1) {
     printf("List Error: delete() called with an undefined index on List\n");
     exit(1);
  }
  if(L->curindex < 0) {
     printf("List Error: delete() called on empty List\n");
     exit(1);
  }
  if(L->cursor == L->front) {
  		deleteFront(L);
  	} else if (L->cursor == L->back) {
  		deleteBack(L);
  	} else {
  		Node left = L->cursor->prev;
  		Node right = L->cursor->next;
  		Node N = L->cursor;
  		left->next = right;
  		right->prev = left;
  		L->cursor = NULL;
  		L->index = -1;
  		L->length--;
  		freeNode(&N);
  	}
  }

//Other operations -------------------------------------------

void printList(FILE* out, List L){
  Node N = NULL;
	if(L == NULL) {
		printf("List Error: calling printList() on NULL List reference\n");
		exit(1);
	}
	for(N = L->front; N != NULL; N = N->next) {
		fprintf(out, "%d ", N->data);
	}
}

List copyList(List L){
  if (L == NULL) {
    printf("List Error: calling copyList() on Null list ref\n");
    exit(1);
 }
  List listcopy = newList();
  Node temp = L->front;
  while(temp != NULL) {
     append(listcopy, temp->data);
     temp = temp->next;
   }
  return listcopy;
}
