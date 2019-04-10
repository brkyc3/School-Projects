/* 						prolab proje 3 labirent oyunu



170201106 burak yazici 										170201112 furkan şahin 

*/

#include <stdio.h>
#include <stdlib.h>


typedef struct {
    int data;
    struct Node *next;
}Node;

void push(Node** headRef ,int data){
    Node * newNode = (Node *)malloc(sizeof(Node));

    newNode->data=data;


    newNode->next=*headRef;
    *headRef=newNode;
}
int pop(Node** headRef){
    if(*headRef==NULL){

            printf("Zaten stack bos!\n");
            return 0;
    }
    Node * temp=*headRef;
    int ret;

    ret=temp->data;


     temp=temp->next;
    free(*headRef);
    *headRef=temp;
    return ret;
}
int peek(Node* head){
     if(head==NULL){

            printf("Stack bos!\n");
            return 0;
    }
    return head->data;
}
int bosmu(Node *head){
return head==NULL ? 1 :0;
}

typedef struct m{
    int yol;
    
    struct map *up;
    struct map *down;
    struct map *left;
    struct map *right;
} map;

void haritayarat(map **root,int x, int y);
map* koortomap(map*root,int a,int b);
void maptokoor(map *m,int *a,int* b,int x,int y);
int dolas(map* iter,int hedefx,int hedefy,int x,int y);
Node* stack1=NULL;
int main()
{
    srand(time(NULL));
    map * root = NULL;
    root = (map*)malloc(sizeof(map));
    map * iterx = root;
    map * itery = root;
    map * temp = root;

    int x,y;
    int i,j;
    printf("harita boyunu girin\nx: ");
    scanf("%d",&x);
    printf("y: ");
    scanf("%d",&y);

    haritayarat(&root,x,y);


    iterx=root;
    itery=root;

    printf("  \t");
    for(i=0;i<10;i+=2)
        printf("%d   ",i);

    for(i=10;i<x;i+=2)
        printf("%d  ",i);

    printf("\n");

    printf("  \t  ");
    for(i=1;i<10;i+=2)
        printf("%d   ",i);

    for(i=11;i<x;i+=2)
        printf("%d  ",i);

    printf("\n");

    for(i=0;i<y;i++){
        printf(" %d\t",i);
        for(j=0;j<x;j++){
            if(iterx->yol==1)
                printf("%c%c",219,219);
            else
                printf("  ");
            
            iterx=iterx->right;

        }
        printf("\n");
        itery = itery->down;
        iterx = itery;
    }


    int girx,giry,cikx,ciky;

    printf("girx = ");
    scanf("%d",&girx);
    printf("giry = ");
    scanf("%d",&giry);
    printf("cikx = ");
    scanf("%d",&cikx);
    printf("ciky =");
    scanf("%d",&ciky);
    dolas(koortomap(root,girx,giry),cikx,ciky,x,y);
    printf("\n\n");
iterx=root;
itery=root;
     printf("  \t");
    for(i=0;i<10;i+=2)
        printf("%d   ",i);

    for(i=10;i<x;i+=2)
        printf("%d  ",i);

    printf("\n");

    printf("  \t  ");
    for(i=1;i<10;i+=2)
        printf("%d   ",i);

    for(i=11;i<x;i+=2)
        printf("%d  ",i);

    printf("\n");

    for(i=0;i<y;i++){
        printf(" %d\t",i);
        for(j=0;j<x;j++){
            if(iterx->yol==1)
                printf("%c%c",219,219);
            else if (iterx->yol==2)
                printf("%c%c",177,177);
            else if (iterx->yol==3)
                printf("%c%c",176,176);
            else
                printf("  ");
            
            iterx=iterx->right;

        }
        printf("\n");
        itery = itery->down;
        iterx = itery;
    }
    return 0;
}

void haritayarat(map **root,int x, int y){

        int i, j;
    map * iterx = *root;
    map * itery = *root;
    map * temp = *root;

    for (i=0;i<y;i++){
            iterx->left=NULL;
        for(j=0;j<x;j++){
            iterx->yol = rand()%2;
            iterx->right = (map*)malloc(sizeof(map));
            temp = iterx;
            iterx = iterx->right;
            iterx->left=temp;
            iterx->right=NULL;
        }
        itery->down = (map*)malloc(sizeof(map));

        temp = itery;
        itery = itery->down;
        itery->up=temp;

        iterx = itery;
    }
int say=0;

    iterx=*root;
    while(iterx!=NULL){
        iterx->up=NULL;
        iterx=iterx->right;
    }
         temp=*root;
    for (i=0;i<y-1;i++){

        iterx=temp->right;
        itery=temp->down;
        itery=itery->right;

       
        do {

            iterx->down=itery;
            itery->up=iterx;

            iterx=iterx->right;
            itery=itery->right;

           say++;
          

        } while(iterx!=NULL);


        temp=temp->down;

    }
     while(itery!=NULL){
        itery->down=NULL;
        itery=itery->right;
    }


printf("*******%d\n",say);
temp=*root;



printf(" %d ",temp->yol);

temp=temp->right;

printf(" %d ",temp->yol);
temp=temp->right;

printf(" %d ",temp->yol);

temp=temp->down;

printf(" %d ",temp->yol);


int a,b;
maptokoor(temp,&a,&b,x,y);

printf("x= %d y= %d \n",a,b);

printf("\n\n %d\n\n",koortomap(*root,3,5)->yol);
}
map* koortomap(map* root,int a,int b){
    for(;a>0;a--)
        root=root->right;
    for(;b>0;b--)
        root=root->down;
    return root;

}
void maptokoor(map *m,int *a,int* b,int x,int y){
*a=0;
*b=0;

while(m->right!=NULL){

    *a=1+*a;
    m=m->right;

}
while(m->down!=NULL){
    *b=1+*b;
    m=m->down;

}
*a=x-*a;
*b=y-1-*b;
}

int dolas(map* iter,int hedefx,int hedefy,int x,int y){


int a,b,top=0;
map* temp=NULL;
iter->yol=3;
maptokoor(iter,&a,&b,x,y);
printf(" **x= %d y= %d   ",a,b);
if(a==hedefx && b==hedefy)return 1;

if(bosmu(stack1));

else{

    top=peek(stack1);

}
temp=iter->right;
if(top !=-2 && temp!=NULL && temp->yol==1){
    push(&stack1,2);
    sag:
    if(dolas(iter->right,hedefx,hedefy,x,y))return 1;
}
temp=iter->down;

if(top !=1 && temp!=NULL&& temp->yol==1){
     push(&stack1,-1);
     alt:
    if(dolas(temp,hedefx,hedefy,x,y))return 1;

}
temp=iter->left;

if(top !=2 &&temp!=NULL&& temp->yol==1){
     push(&stack1,-2);
     sol:
    if(dolas(temp,hedefx,hedefy,x,y))return 1;
}
temp=iter->up;

if(top !=-1 &&temp!=NULL&& temp->yol==1){
    push(&stack1,1);
    ust:
    if(dolas(temp,hedefx,hedefy,x,y))return 1;

}
iter->yol=2;
switch(pop(&stack1)){
case 2:
    temp=iter->left;
    goto sol;
    break;
case -1:
    temp=iter->up;
    goto ust;
    break;
case -2:
    temp=iter->right;
    goto sag;
    break;
case 1:
    temp=iter->down;
    goto alt;
    break;
case 0:
    return 0;

}

}

