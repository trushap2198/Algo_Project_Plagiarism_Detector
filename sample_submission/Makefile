# the compiler: gcc for C, g++ for C++
CC = g++

# compiler flags:
CFLAGS = -Wall -O3

# build target
TARGET = student_id_detector

FILE1 =
FILE2 = 

all: $(TARGET)

$(TARGET): $(TARGET).cpp
	$(CC) $(CFLAGS) -o $(TARGET) $(TARGET).cpp

clean:
	$(RM) $(TARGET)

run: $(TARGET)
	@./$(TARGET) $(FILE1) $(FILE2)

testPlagiarism: $(TARGET)
	@echo "Testing plagiarism test cases in ../data/plagiarismXX"
	@for file in ../data/plagiarism*; do echo "Testing $$file"; ./$(TARGET) $$file/1.txt $$file/2.txt;done

testNonPlagiarism: $(TARGET)
	@echo "Testing non-plagiarism test cases in ../data/okayXX"
	@for file in ../data/okay*; do echo "Testing $$file"; ./$(TARGET) $$file/1.txt $$file/2.txt;done

test: testPlagiarism testNonPlagiarism
