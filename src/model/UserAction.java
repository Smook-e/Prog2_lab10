/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class UserAction {
    private int row;
    private int column;
    private int num;
    private int prevNum;
    public UserAction(int row, int column, int num,int prevNum)
    {
        this.row=row;
        this.column=column;
        this.num=num;
        this.prevNum=prevNum;
    }
    public int getRow(){return row;}
    public int getColumn(){return column;}
    public int getNum(){return num;}
    public int getPrevNum(){return prevNum;}
 }
