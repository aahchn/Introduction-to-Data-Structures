//Test for Dictionary ADT

#include<stdio.h>
#include<stdlib.h>
#include "Dictionary.h"


int main(int arc, char* argv[]){
  int x;
  DictionaryRef test = newDictionary();
  
  insert(test, 1,1);
  insert(test, 2,2 );
  insert(test, 2,1 );
  insert(test, 3,4);
  insert(test, 9, 4);
  insert(test, 8, 55);
  insert(test, 5, 6);
  insert(test, 12,13);
  printDictionary(test, stdout);
  
  delete(test,8);
  delete(test,1);
  delete(test,2);
  printDictionary(test,stdout);

  
  x = lookup(test, 4);
  printf("%d\n",x);
  x = lookup(test,2);
  printf("%d\n",x);
  
  makeEmpty(test);
  printDictionary(test,stdout);
  
  freeDictionary(&test);
  return (EXIT_SUCCESS);

}
