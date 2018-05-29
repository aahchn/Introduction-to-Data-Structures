/* Aaron Chan - aahchan
 * charType.c
 * 5/2/16
 * charType.c
 * takes two command line arguments giving the input and output file
 *  names respectively, then classifies the characters on each 
 *  line of the input file alphabetically, numeric, & punctuation.
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <assert.h>
#include <string.h>
#define MAX_STRING_LENGTH 100

// function prototype 
void extract_chars(char* s, char* a, char* d, char* p, char* w);

// function main which takes command line arguments 
int main(int argc, char* argv[]){
  FILE* in;         // handle for input file    
  FILE* out;        // handle for output file 
  char* line;       // string holding input line              
  char* word;       // stores alphabetical characters
  char* number;     // stores numerical characters
  char* punctuation;// stores punctuations characters
  char* whitespace; // stores white space
  int n = 1;        // line number in file

  // check command line for correct number of arguments 
  if(argc != 3){
    printf("Usage: %s input-file outputfile \n", argv[0]);
    exit(EXIT_FAILURE);
  }

  // open input file for reading 
  if( (in = fopen(argv[1], "r")) == NULL ){
    printf("Unable to read from file %s\n", argv[1]);
    exit(EXIT_FAILURE);
  }

  // open output file for writing 
  if( (out = fopen(argv[2], "w")) == NULL ){
    printf("Unable to write to file %s\n", argv[2]);
    exit(EXIT_FAILURE);
  }

  // allocates heap memory for character arrays
  line = calloc(MAX_STRING_LENGTH+1, sizeof(char));
  word = calloc(MAX_STRING_LENGTH+1, sizeof(char));
  number = calloc(MAX_STRING_LENGTH+1, sizeof(char));
  punctuation = calloc(MAX_STRING_LENGTH+1, sizeof(char));
  whitespace = calloc(MAX_STRING_LENGTH+1, sizeof(char));
  assert(line != NULL && word != NULL && number != NULL && punctuation != NULL && whitespace != NULL);


  // read each line in input file, extract alpha-numeric characters 
  while( fgets(line, MAX_STRING_LENGTH, in) != NULL ){
    extract_chars(line, word, number, punctuation, whitespace);
    fprintf(out, "line %d contains: \n",n);
    if(strlen(word) > 1){
	fprintf(out,"%d alphabetic characters: %s\n",(int)strlen(word), word);
    }
    else{
	fprintf(out,"%d alphabetic character: %s\n",(int)strlen(word), word);
      }
    if(strlen(number) > 1){
	fprintf(out,"%d numeric characters: %s\n",(int)strlen(number), number);
      }
    else{
	  fprintf(out,"%d numeric character: %s\n",(int)strlen(number), number);
    }
    if(strlen(punctuation) > 1){
	fprintf(out,"%d punctuation characters: %s\n",(int)strlen(punctuation),punctuation);
    }
    else{
	fprintf(out,"%d punctuation character: %s\n",(int)strlen(punctuation),punctuation);
    }
    if(strlen(whitespace) > 1){
	fprintf(out,"%d whitespace characters: %s\n",(int)strlen(whitespace),whitespace);
    }
    else{
	fprintf(out,"%d whitespace character: %s\n",(int)strlen(whitespace),whitespace);
    }
      n++; //increments line number
  }

    //free the heap memory
    free(line);
    free(word);
    free(number);
    free(punctuation);
    free(whitespace);

    //closes file
    fclose(in);
    fclose(out);

    return EXIT_SUCCESS;
  }

  void extract_chars(char* s, char* a, char* d, char* p, char* w){
    int i=0, j=0, k=0, l=0, m=0;
    while(s[i] != '\0' && i<MAX_STRING_LENGTH){
      if( isupper((int)s[i])){
	a[j]=s[i];
	j++;
      }
      else if( isalpha((int)s[i])){
	a[j] =s[i];
	j++;
      }
      else if( isdigit((int)s[i])){
	d[k] = s[i];
	k++;
      }
      else if(ispunct((int)s[i])){
	p[l]=s[i];
	l++;
      }
      else{
	w[m]=s[i];
	m++;
      }
      i++;
    }
    /*sets a null character at the end of the arrays*/
    a[j]='\0';
    d[k]='\0';
    p[l]='\0';
    w[m]='\0';
  }
