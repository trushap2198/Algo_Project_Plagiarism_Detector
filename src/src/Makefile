JFLAGS = -g
JC = javac
JVM= java
FILE1=
FILE2=

.SUFFIXES: .java .class


.java.class:
        $(JC) $(JFLAGS) $*.java


CLASSES = \
        KMP.java \
        Plagiarism_detector_LCS.java \
        PlagiarismChecker.java \
        Detector_40192614.java

MAIN = Detector_40192614.java

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE)
