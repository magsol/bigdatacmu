HW4a: Naive Bayes on Hadoop
===========================

This is a colleciton of Java files using the Hadoop framework to build a 
distributed version of the Naive Bayes classifier. It consists of four MR
tasks: two for training, one for joining the trained model to the test data,
and one for classification.

To create the .jar archive:

    javac *.java
    jar cf NBTask.jar *.class

To run the job:

    hadoop jar NBTask.jar NBController /path/to/training/data /path/to/test/data /output/dir
    
