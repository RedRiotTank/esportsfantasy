
# eSportsFantasy Frontend

eSportsFantasy is a web and mobile application that implements a "fantasy" style game model in the world of electronic sports.

This is also my final degree project for the degree in Computer Engineering at the University of Granada.

In this repository you can find the frontend system of the application.




## Deployment

To deploy this project, you can do it in two different ways

### All containers in the same server
//TODO

### Each container separately

To deploy the frontend part of the project, clone the repository

```bash
  git clone https://github.com/RedRiotTank/esportsfantasyfe.git
```

create the docker image and run it:

```bash
docker build -t esportsfantasyfe .
docker run -d --name be -p 80:4000 fe
```