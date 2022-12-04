JFLAGS = -g
JC = javac
JVM= java
FILE1=
FILE2=

.SUFFIXES: .java .class


.java.class: ; $(JC) $(JFLAGS) $*.java


CLASSES = \
        Stemmer.java \
        Detector_40192614.java

MAIN = Detector_40192614.java

default: classes

classes: $(CLASSES:.java=.class)

 run: classes
	$(JVM) $(MAIN) $(FILE1) $(FILE2)
