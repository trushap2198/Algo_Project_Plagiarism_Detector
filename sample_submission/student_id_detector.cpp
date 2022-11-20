#include <fstream>
#include <string>
#include <vector>
#include <iostream>
using namespace std;

vector<string> readFile(char * filename);
bool detect(vector<string> &f1, vector<string> &f2);

/*
 * Accepts two arguments: paths to two text files to be processed.
 * Prints to standard output 1 if plagiarism is detected and 0 otherwise.
 * Prints -1 if error is encountered.
 */
int main(int argc, char **argv) {
  if(argc != 3) {
    cout << "-1" << endl;
    return -1;
  }

  vector<string> f1 = readFile(argv[1]);
  vector<string> f2 = readFile(argv[2]);

  // detect plagiarism and print to the screen
  if(detect(f1, f2)) {
    cout << "1" << endl;
  } else {
    cout << "0" << endl;
  }

  return 0;
}


vector<string> readFile(char *fname) {
  vector<string> result;

  // READ FILE HERE
  
  return result;
}


bool detect(vector<string> &f1, vector<string> &f2) {
  
  // PLAGIARISM DETECTION LOGIC GOES HERE
  
  return false;
}
