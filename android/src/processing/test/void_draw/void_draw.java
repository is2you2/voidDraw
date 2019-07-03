package processing.test.void_draw;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class void_draw extends PApplet {

/* Project Cone: void_draw for light drawing on phone
   Copyright(C) 2016. liss22, Sungsoo Choi
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/> */

PImage point,Logo,save;
int dr=60,rw=0,ma=255,roll=0;
String states="None";
String[] stname={"Drawing","Color","None"};
float[] c={0,0,30};
float distance;
PVector pp;

public void setup(){
  
  //size(432,768);
  background(255);
  Logo=loadImage("Cone.png");
  save=loadImage("new.png");
  point=loadImage("paint.png");
  colorMode(HSB,100,100,100);
  ellipseMode(CENTER);
  
  menu();
  save("tmp.png");
}

public void draw(){
  ArrayList<PVector> pot=new ArrayList<PVector>();
  if(states=="Drawing"&&mousePressed){
    if(pp!=null){
      stroke(c[0],c[1],c[2],180);
      noFill();
      strokeWeight(dist(mouseX,mouseY,pmouseX,pmouseY)/32);
      bezier(pp.x,pp.y,pmouseX,pmouseY,pmouseX,pmouseY,mouseX,mouseY);
      bezier(pp.x,pp.y,pp.x,pp.y,pmouseX,pmouseY,mouseX,mouseY);
    }
    pp=new PVector(pmouseX,pmouseY);
  }
  if(states=="Color"){
    if(rw<width+35){
      fill(0,5);
      noStroke();
      rect(0,0,width,height);
      fill(c[0],c[1],c[2]);
      rect(0,dr,rw,25);
      if(roll<=25){
        for(int i=0;i<roll;i++){
          pushMatrix();
          translate(width/2,height/2);
          rotate(radians(-60+(5*i)));
          stroke(4*i,100,100);
          strokeWeight(3);
          line(0,-145,0,-235);
          popMatrix();
         
        }
      }
      roll+=1;
      rw+=(width+rw)/35;
      for(int i=0;i<roll;i++){
        fill(50+roll*1.5f);
        noStroke();
        for(int j=0;j<5;j++){
          ellipse(width/8+width/33.5f*i,height/2+150+j*10,5-PApplet.parseFloat(i)/13,5-PApplet.parseFloat(i)/13);
        }
      }
      fill(100);
      strokeWeight(5);
      stroke(0);
      ellipse(width/2,height/2,roll*6,roll*6);
    }
    if(mousePressed&&rw>width+35){
      if(mouseY<height/2-60){
        pushMatrix();
        translate(width/2,height/2);
        c[0]=map(degrees(atan2(mouseY-height/2,mouseX-width/2)),-150,-30,0,100);
        popMatrix();
        if(c[0]<0) {c[0]=0;}
        if(c[0]>100) {c[0]=100;}
        if(dist(width/2,height/2,mouseX,mouseY)>120){
          c[1]=dist(width/2,height/2,mouseX,mouseY)-145;
          if(c[1]<0) {c[1]=0;}
          if(c[1]>100) {c[1]=100;}
        }
      }
      if(mouseY>height/2+130&&mouseY<height/2+220){
        c[2]=map(mouseX,width/8,width*7/8,0,100);
        if(c[2]<0) {c[2]=0;}
        if(c[2]>100) {c[2]=100;}
      }
      fill(c[0],c[1],c[2]);
      noStroke();
      rect(0,dr,rw,25);
    }
  }
}

public void mousePressed(){
  if(mouseY>dr){
    if(states!="Color"){
      states=stname[0];
    }
  }else if(mouseX<width/2){
    states=stname[1]; 
  }else{
    background(100);
    rw=0;
    roll=0;
    menu();
    states=stname[2];
  }
}

public void mouseReleased(){
  if(states=="Drawing"){
    save("tmp.png");
    menu();
    states=stname[2];
  }
  if(states=="Color"&&dist(width/2,height/2,mouseX,mouseY)<80){
    imageMode(CORNER);
    image(loadImage("tmp.png"),0,0);
    roll=0;
    rw=0;
    menu();
    states=stname[0];
  }
  pp=null;
}

public void menu(){
  imageMode(CENTER);
  strokeWeight(1);
  stroke(0);
  fill(95);
  rect(0,0,width/2,dr);
  image(Logo,width/4,dr/2,Logo.width/2,Logo.height/2);
  rect(width/2,0,width,dr);
  image(save,width*3/4,dr/2,save.width/2,save.height/2);
}
  public void settings() {  size(displayWidth,displayHeight);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "void_draw" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
