** User File Storage complete with adding users, uploading profile images for users ** 

* Can add users, upload images for each user, when you upload an image through the React Front end, it communicates with the Spring Boot Back end to upload the profile images to an S3 bucket, and then for each user
* it retrives the image by download and displays it * 
* Uses React for front end and Java Spring Boot for the back end and Amazon S3 for file storage, upload, and dowload*

* Might add functionality for different type of files such as PDFs for each user, maybe also login and sign up * 


** HOW TO RUN ** 

Not hosted as a website yes but can be run by 
First install the necessary dependcies npm, Springboot, react , react dropzone, etc, whatever is needed. 
Have an AmazonConfig.java file with the necessary amazon credentials for the S3 bucket. 
Then run the front end by first traversing to the directory image-upload-aws/src/main/frontend and then running the command "npm start"
Then run the back end by running the java main image-upload-aws/src/main/java and running Main.java

