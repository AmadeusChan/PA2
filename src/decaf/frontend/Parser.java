//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short COMPLEX=261;
public final static short CLASS=262;
public final static short NULL=263;
public final static short EXTENDS=264;
public final static short THIS=265;
public final static short SUPER=266;
public final static short WHILE=267;
public final static short FOR=268;
public final static short IF=269;
public final static short ELSE=270;
public final static short RETURN=271;
public final static short BREAK=272;
public final static short NEW=273;
public final static short CASE=274;
public final static short DEFAULT=275;
public final static short PRINT=276;
public final static short READ_INTEGER=277;
public final static short READ_LINE=278;
public final static short PRINTCOMP=279;
public final static short LITERAL=280;
public final static short IDENTIFIER=281;
public final static short AND=282;
public final static short OR=283;
public final static short STATIC=284;
public final static short INSTANCEOF=285;
public final static short LESS_EQUAL=286;
public final static short GREATER_EQUAL=287;
public final static short EQUAL=288;
public final static short NOT_EQUAL=289;
public final static short DCOPY=290;
public final static short SCOPY=291;
public final static short DO=292;
public final static short OD=293;
public final static short DOSEPERATOR=294;
public final static short UMINUS=295;
public final static short EMPTY=296;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   22,   23,
   23,   24,   14,   14,   14,   28,   28,   26,   26,   27,
   30,   31,   31,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   32,   32,   29,   29,
   33,   33,   16,   17,   20,   15,   34,   34,   18,   18,
   19,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    2,    4,    3,
    0,    3,    3,    1,    0,    2,    0,    2,    4,    5,
    4,    5,    0,    4,    4,    1,    8,    1,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    2,    2,    2,    3,
    3,    1,    4,    5,    6,    5,    1,    1,    1,    0,
    3,    1,    5,    9,    1,    6,    2,    0,    2,    1,
    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   88,   82,   56,    0,
    0,    0,    0,   95,    0,    0,    0,    0,    0,    0,
   87,    0,    0,    0,   41,    0,    0,    0,   25,    0,
    0,    0,   28,   36,   26,    0,   30,   31,   32,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   60,    0,
    0,    0,    0,   58,   59,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   77,   78,   79,   29,   33,   34,   35,   37,   38,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   46,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   80,   81,    0,    0,    0,    0,
    0,    0,    0,   74,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   83,    0,    0,    0,  101,  102,    0,
   54,   55,   39,   40,    0,    0,   49,    0,    0,   93,
    0,    0,   84,   53,    0,    0,   42,   86,   50,    0,
    0,   96,    0,   85,    0,   97,    0,    0,    0,    0,
    0,   57,    0,   94,    0,    0,   51,   52,
};
final static short yydgoto[] = {                          2,
    3,    4,   73,   21,   34,    8,   11,   23,   35,   36,
   74,   46,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,  106,  151,   85,   94,   95,   88,  188,  208,
  203,   89,  144,  202,
};
final static short yysindex[] = {                      -253,
 -258,    0, -253,    0, -225,    0, -228,  -80,    0,    0,
  -47,    0,    0,    0,    0,    0, -224, -113,    0,    0,
    1,  -87,    0,    0,  -83,    0,   25,   -9,   26, -113,
    0, -113,    0,  -74,   44,   43,   47,    0,  -30, -113,
  -30,    0,    0,    0,    0,    5,    0,    0,    0,   51,
   54,   59,  117,    0,  331,   61,   63,   68,   69,   70,
    0,   71,   73,   76,    0,  117,  117,   62,    0,  117,
  117,  117,    0,    0,    0,   58,    0,    0,    0,   66,
   74,   79,   81,   83,  916,   60,    0, -154,    0,  117,
  117,  117,  916,    0,    0,   95,   64,  117,  117,  110,
  113,  117,  117,  117,  117,  117,  -27,  -27, -125,  505,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  117,
  117,  117,  117,  117,  117,  117,  117,  117,  117,  117,
  117,  117,    0,  117,  117,  118,  516,  100,  538,  120,
   96,  568,  916,   29,    0,    0,   31,  579,  590,  601,
 -245,  662,  122,    0,   77,  940,    9,    9,  -32,  -32,
   21,   21,  -27,  -27,  -27,    9,    9,  849,  916,  117,
   41,  117,   41,    0,  861,   53,  117,    0,    0, -115,
    0,    0,    0,    0,   41,  117,    0,  126,  130,    0,
  709,  -93,    0,    0,  916,  137,    0,    0,    0,  117,
   41,    0, -238,    0,  139,    0,  125,   67,  127,   41,
  117,    0,  117,    0,  769,  873,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  184,    0,   85,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  132,    0,    0,  152,
    0,  152,    0,    0,    0,  165,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,    0,  -39,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -72,  -72,  -72,    0,  -72,
  -72,  -72,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  928,  478,    0,    0,  -72,
  -58,  -72,  163,    0,    0,    0,    0,  -72,  -72,    0,
    0,  -72,  -72,  -72,  -72,  -72,  128,  390,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -72,
  -72,  -72,  -72,  -72,  -72,  -72,  -72,  -72,  -72,  -72,
  -72,  -72,    0,  -72,  -72,  158,    0,    0,    0,    0,
  -72,    0,   38,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  138,    3,  560,  800,  384,  630,
  967,  977,  414,  443,  452,  841, 1003,    0,  -23,  -25,
  -58,  -72,  -58,    0,    0,    0,  -72,    0,    0,    0,
    0,    0,    0,    0,  -58,  -72,    0,    0,  209,    0,
    0,  -33,    0,    0,   39,    0,    0,    0,    0,  -12,
  -58,    0,    0,    0,    0,    0,    0,    0,    0,  -58,
  -72,    0,  -72,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  250,  264,   -8,   16,    0,    0,    0,  248,    0,
   -6,    0,  -67,  -85,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, 1204,  333,  483,    0,    0,    0,
    0,   84,  -81,    0,
};
final static int YYTABLESIZE=1417;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         98,
   45,   98,   98,   28,  130,  138,   98,   28,    1,  128,
  126,   98,  127,  133,  129,   90,   28,   43,  133,  100,
  147,   33,    5,   33,   47,   98,   22,  132,   45,  131,
   98,   44,   43,   25,   45,   43,  207,   67,    7,   72,
   71,   61,   10,   73,   68,  130,   73,  183,  184,   66,
  128,  126,    9,  127,  133,  129,   24,  130,  134,   26,
   73,   73,  128,  134,   30,   32,  133,  129,   70,  178,
   97,  179,  177,   67,  177,   72,   71,   19,   92,   91,
   68,   92,   91,   31,   39,   66,   40,   41,  189,   98,
   90,   98,   42,   91,   67,   73,   72,   71,   92,  134,
   98,   68,   99,  190,   70,  192,   66,  100,  101,  102,
  103,  134,  104,  130,  205,  105,  114,  197,  128,  126,
  135,  127,  133,  129,  115,   70,  136,   42,   67,   69,
   72,   71,  116,  206,  140,   68,  132,  117,  131,  118,
   66,  119,  214,   12,   13,   14,   15,   16,   17,   67,
  145,   72,   71,  146,  141,  153,   68,  170,  172,   70,
  174,   66,  186,   42,   75,  196,  199,  134,   75,   75,
   75,   75,   75,  177,   75,  194,  201,  204,   72,  210,
   70,   72,  211,    1,  213,   75,   75,   75,   31,   75,
    5,  212,   20,   27,   48,   72,   72,   29,   48,   48,
   48,   48,   48,   48,   48,   19,   38,   15,   47,   12,
   13,   14,   15,   16,   17,   48,   48,   48,   48,   48,
   75,   99,   47,   98,   98,   98,   98,   98,   98,   98,
   72,   98,   98,   98,   98,   98,   18,   98,   98,   98,
   98,   47,   98,   98,   98,   98,   98,   98,   48,   89,
   48,   98,    6,  122,  123,   47,   98,   98,   98,   98,
   98,   12,   13,   14,   15,   16,   17,   47,   47,   48,
   49,   50,   51,   52,   20,   53,   54,   55,   56,   37,
   57,   58,   59,   60,   61,   73,  209,    0,    0,   62,
    0,    0,    0,    0,   63,   64,   65,   12,   13,   14,
   15,   16,   17,   47,    0,   48,   49,   50,   51,   52,
    0,   53,   54,   55,   56,    0,   57,   58,   59,   60,
   61,    0,    0,  109,   47,   62,   48,   49,    0,    0,
   63,   64,   65,    0,   55,   56,    0,    0,   58,   59,
    0,   61,    0,    0,    0,    0,   62,    0,    0,    0,
    0,   63,   64,    0,    0,    0,    0,    0,   47,    0,
   48,   49,  122,  123,  124,  125,    0,    0,   55,   56,
    0,    0,   58,   59,    0,   61,    0,    0,   86,   47,
   62,   48,   49,    0,    0,   63,   64,    0,    0,   55,
   56,    0,    0,   58,   59,    0,   61,    0,    0,    0,
    0,   62,    0,    0,    0,    0,   63,   64,    0,   75,
   75,    0,    0,   75,   75,   75,   75,    0,    0,   72,
   72,    0,    0,   86,   66,    0,   76,   66,    0,    0,
   76,   76,   76,   76,   76,    0,   76,    0,    0,   48,
   48,   66,   66,   48,   48,   48,   48,   76,   76,   76,
   63,   76,    0,    0,   63,   63,   63,   63,   63,    0,
   63,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   63,   63,   63,    0,   63,   66,    0,    0,   64,
    0,    0,   76,   64,   64,   64,   64,   64,   65,   64,
    0,    0,   65,   65,   65,   65,   65,    0,   65,    0,
   64,   64,   64,   86,   64,   86,   63,    0,    0,   65,
   65,   65,    0,   65,   59,    0,    0,   86,   44,   59,
   59,    0,   59,   59,   59,    0,    0,    0,   87,    0,
    0,    0,   86,   86,    0,   64,   44,   59,    0,   59,
    0,  130,   86,    0,   65,  154,  128,  126,    0,  127,
  133,  129,  130,    0,    0,    0,  171,  128,  126,    0,
  127,  133,  129,    0,  132,    0,  131,    0,   59,    0,
    0,    0,    0,   87,  130,  132,    0,  131,  173,  128,
  126,    0,  127,  133,  129,    0,    0,   12,   13,   14,
   15,   16,   17,    0,    0,  134,    0,  132,    0,  131,
   70,    0,    0,   70,  130,    0,  134,    0,  176,  128,
  126,   96,  127,  133,  129,  130,    0,   70,   70,    0,
  128,  126,  180,  127,  133,  129,  130,  132,  134,  131,
  181,  128,  126,    0,  127,  133,  129,  130,  132,    0,
  131,  182,  128,  126,    0,  127,  133,  129,    0,  132,
    0,  131,   70,   87,    0,   87,    0,    0,  134,    0,
  132,    0,  131,    0,    0,   66,   66,   87,    0,  134,
   67,   76,   76,   67,    0,   76,   76,   76,   76,    0,
  134,    0,   87,   87,    0,    0,    0,   67,   67,    0,
    0,  134,   87,    0,    0,   63,   63,    0,  130,   63,
   63,   63,   63,  128,  126,    0,  127,  133,  129,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  185,
    0,  132,   67,  131,   64,   64,    0,    0,   64,   64,
   64,   64,    0,   65,   65,    0,    0,   65,   65,   65,
   65,    0,    0,    0,    0,  130,    0,    0,    0,    0,
  128,  126,  134,  127,  133,  129,    0,    0,    0,   59,
   59,    0,    0,   59,   59,   59,   59,  200,  132,    0,
  131,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  120,  121,    0,    0,
  122,  123,  124,  125,    0,    0,    0,  120,  121,  134,
    0,  122,  123,  124,  125,  130,    0,    0,    0,    0,
  128,  126,    0,  127,  133,  129,    0,    0,    0,  120,
  121,    0,    0,  122,  123,  124,  125,  217,  132,    0,
  131,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   71,   70,   70,   71,    0,    0,    0,   70,   70,  120,
  121,    0,    0,  122,  123,  124,  125,   71,   71,  134,
  120,  121,    0,    0,  122,  123,  124,  125,    0,    0,
    0,  120,  121,    0,    0,  122,  123,  124,  125,    0,
    0,   69,  120,  121,   69,  130,  122,  123,  124,  125,
  128,  126,   71,  127,  133,  129,    0,  130,   69,   69,
    0,    0,  128,  126,    0,  127,  133,  129,  132,  130,
  131,   67,   67,    0,  128,  126,    0,  127,  133,  129,
  132,    0,  131,    0,    0,    0,    0,    0,    0,    0,
    0,  218,  132,   69,  131,    0,    0,    0,    0,  134,
    0,  187,    0,  120,  121,    0,    0,  122,  123,  124,
  125,  134,  130,  193,    0,    0,    0,  128,  126,    0,
  127,  133,  129,  134,   58,    0,    0,    0,    0,   58,
   58,    0,   58,   58,   58,  132,  130,  131,    0,    0,
    0,  128,  126,    0,  127,  133,  129,   58,    0,   58,
  120,  121,    0,    0,  122,  123,  124,  125,    0,  132,
    0,  131,    0,    0,    0,    0,  134,   61,    0,   61,
   61,   61,    0,    0,    0,    0,    0,   62,   58,   62,
   62,   62,    0,    0,   61,   61,   61,    0,   61,    0,
  134,    0,    0,    0,   62,   62,   62,    0,   62,    0,
    0,    0,    0,   68,    0,    0,   68,    0,    0,    0,
  120,  121,    0,    0,  122,  123,  124,  125,    0,   61,
   68,   68,    0,    0,    0,    0,    0,    0,    0,   62,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   71,   71,    0,    0,    0,    0,   71,   71,    0,
    0,    0,    0,    0,    0,   68,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   69,   69,    0,    0,    0,    0,   69,   69,
  120,  121,    0,    0,  122,  123,  124,  125,    0,    0,
    0,    0,  120,  121,    0,    0,  122,  123,  124,  125,
    0,    0,    0,    0,  120,  121,    0,    0,  122,  123,
  124,  125,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  120,  121,    0,
    0,  122,  123,  124,  125,    0,    0,    0,    0,   58,
   58,    0,    0,   58,   58,   58,   58,    0,    0,    0,
    0,  120,    0,    0,    0,  122,  123,  124,  125,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   61,   61,
    0,    0,   61,   61,   61,   61,   93,    0,   62,   62,
    0,    0,   62,   62,   62,   62,    0,    0,    0,  107,
  108,  110,    0,  111,  112,  113,    0,    0,    0,    0,
    0,    0,    0,    0,   68,   68,    0,    0,    0,    0,
   68,   68,    0,  137,    0,  139,    0,    0,    0,    0,
    0,  142,  143,    0,    0,  143,  148,  149,  150,  152,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  155,  156,  157,  158,  159,  160,  161,
  162,  163,  164,  165,  166,  167,    0,  168,  169,    0,
    0,    0,    0,    0,  175,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  143,    0,  191,    0,    0,    0,    0,
  195,    0,    0,    0,    0,    0,    0,    0,    0,  198,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  215,    0,  216,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   35,   36,   91,   37,   91,   40,   91,  262,   42,
   43,   45,   45,   46,   47,   41,   91,   41,   46,   59,
  102,   30,  281,   32,  263,   59,   11,   60,   41,   62,
   64,   40,   39,   18,   41,   59,  275,   33,  264,   35,
   36,  280,  123,   41,   40,   37,   44,  293,  294,   45,
   42,   43,  281,   45,   46,   47,  281,   37,   91,   59,
   58,   59,   42,   91,   40,   40,   46,   47,   64,   41,
   55,   41,   44,   33,   44,   35,   36,  125,   41,   41,
   40,   44,   44,   93,   41,   45,   44,   41,  170,  123,
   40,  125,  123,   40,   33,   93,   35,   36,   40,   91,
   40,   40,   40,  171,   64,  173,   45,   40,   40,   40,
   40,   91,   40,   37,  200,   40,   59,  185,   42,   43,
   61,   45,   46,   47,   59,   64,  281,  123,   33,  125,
   35,   36,   59,  201,   40,   40,   60,   59,   62,   59,
   45,   59,  210,  257,  258,  259,  260,  261,  262,   33,
   41,   35,   36,   41,   91,  281,   40,   40,   59,   64,
   41,   45,   41,  123,   37,  281,   41,   91,   41,   42,
   43,   44,   45,   44,   47,  123,  270,   41,   41,   41,
   64,   44,   58,    0,   58,   58,   59,   60,   93,   62,
   59,  125,   41,  281,   37,   58,   59,  281,   41,   42,
   43,   44,   45,   46,   47,   41,  281,  123,  281,  257,
  258,  259,  260,  261,  262,   58,   59,   60,   61,   62,
   93,   59,  281,  257,  258,  259,  260,  261,  262,  263,
   93,  265,  266,  267,  268,  269,  284,  271,  272,  273,
  274,  281,  276,  277,  278,  279,  280,  281,   91,   41,
   93,  285,    3,  286,  287,  281,  290,  291,  292,  293,
  294,  257,  258,  259,  260,  261,  262,  263,  281,  265,
  266,  267,  268,  269,   11,  271,  272,  273,  274,   32,
  276,  277,  278,  279,  280,  283,  203,   -1,   -1,  285,
   -1,   -1,   -1,   -1,  290,  291,  292,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,  269,
   -1,  271,  272,  273,  274,   -1,  276,  277,  278,  279,
  280,   -1,   -1,  262,  263,  285,  265,  266,   -1,   -1,
  290,  291,  292,   -1,  273,  274,   -1,   -1,  277,  278,
   -1,  280,   -1,   -1,   -1,   -1,  285,   -1,   -1,   -1,
   -1,  290,  291,   -1,   -1,   -1,   -1,   -1,  263,   -1,
  265,  266,  286,  287,  288,  289,   -1,   -1,  273,  274,
   -1,   -1,  277,  278,   -1,  280,   -1,   -1,   46,  263,
  285,  265,  266,   -1,   -1,  290,  291,   -1,   -1,  273,
  274,   -1,   -1,  277,  278,   -1,  280,   -1,   -1,   -1,
   -1,  285,   -1,   -1,   -1,   -1,  290,  291,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,  282,
  283,   -1,   -1,   91,   41,   -1,   37,   44,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,  282,
  283,   58,   59,  286,  287,  288,  289,   58,   59,   60,
   37,   62,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,   60,   -1,   62,   93,   -1,   -1,   37,
   -1,   -1,   93,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   58,   59,   60,  171,   62,  173,   93,   -1,   -1,   58,
   59,   60,   -1,   62,   37,   -1,   -1,  185,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   46,   -1,
   -1,   -1,  200,  201,   -1,   93,   59,   60,   -1,   62,
   -1,   37,  210,   -1,   93,   41,   42,   43,   -1,   45,
   46,   47,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   60,   -1,   62,   -1,   91,   -1,
   -1,   -1,   -1,   91,   37,   60,   -1,   62,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,  257,  258,  259,
  260,  261,  262,   -1,   -1,   91,   -1,   60,   -1,   62,
   41,   -1,   -1,   44,   37,   -1,   91,   -1,   41,   42,
   43,  281,   45,   46,   47,   37,   -1,   58,   59,   -1,
   42,   43,   44,   45,   46,   47,   37,   60,   91,   62,
   41,   42,   43,   -1,   45,   46,   47,   37,   60,   -1,
   62,   41,   42,   43,   -1,   45,   46,   47,   -1,   60,
   -1,   62,   93,  171,   -1,  173,   -1,   -1,   91,   -1,
   60,   -1,   62,   -1,   -1,  282,  283,  185,   -1,   91,
   41,  282,  283,   44,   -1,  286,  287,  288,  289,   -1,
   91,   -1,  200,  201,   -1,   -1,   -1,   58,   59,   -1,
   -1,   91,  210,   -1,   -1,  282,  283,   -1,   37,  286,
  287,  288,  289,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   -1,   60,   93,   62,  282,  283,   -1,   -1,  286,  287,
  288,  289,   -1,  282,  283,   -1,   -1,  286,  287,  288,
  289,   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,
   42,   43,   91,   45,   46,   47,   -1,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  282,  283,   -1,   -1,
  286,  287,  288,  289,   -1,   -1,   -1,  282,  283,   91,
   -1,  286,  287,  288,  289,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,  282,  283,   44,   -1,   -1,   -1,  288,  289,  282,
  283,   -1,   -1,  286,  287,  288,  289,   58,   59,   91,
  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,
   -1,  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,
   -1,   41,  282,  283,   44,   37,  286,  287,  288,  289,
   42,   43,   93,   45,   46,   47,   -1,   37,   58,   59,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   60,   37,
   62,  282,  283,   -1,   42,   43,   -1,   45,   46,   47,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   59,   60,   93,   62,   -1,   -1,   -1,   -1,   91,
   -1,   93,   -1,  282,  283,   -1,   -1,  286,  287,  288,
  289,   91,   37,   93,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   91,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   60,   37,   62,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   60,   -1,   62,
  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,   60,
   -1,   62,   -1,   -1,   -1,   -1,   91,   41,   -1,   43,
   44,   45,   -1,   -1,   -1,   -1,   -1,   41,   91,   43,
   44,   45,   -1,   -1,   58,   59,   60,   -1,   62,   -1,
   91,   -1,   -1,   -1,   58,   59,   60,   -1,   62,   -1,
   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,
  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,   93,
   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  282,  283,   -1,   -1,   -1,   -1,  288,  289,   -1,
   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  282,  283,   -1,   -1,   -1,   -1,  288,  289,
  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,
   -1,   -1,  282,  283,   -1,   -1,  286,  287,  288,  289,
   -1,   -1,   -1,   -1,  282,  283,   -1,   -1,  286,  287,
  288,  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,  283,   -1,
   -1,  286,  287,  288,  289,   -1,   -1,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,   -1,
   -1,  282,   -1,   -1,   -1,  286,  287,  288,  289,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,  283,
   -1,   -1,  286,  287,  288,  289,   53,   -1,  282,  283,
   -1,   -1,  286,  287,  288,  289,   -1,   -1,   -1,   66,
   67,   68,   -1,   70,   71,   72,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  282,  283,   -1,   -1,   -1,   -1,
  288,  289,   -1,   90,   -1,   92,   -1,   -1,   -1,   -1,
   -1,   98,   99,   -1,   -1,  102,  103,  104,  105,  106,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  120,  121,  122,  123,  124,  125,  126,
  127,  128,  129,  130,  131,  132,   -1,  134,  135,   -1,
   -1,   -1,   -1,   -1,  141,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  170,   -1,  172,   -1,   -1,   -1,   -1,
  177,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  186,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  211,   -1,  213,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=296;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,"'#'","'$'","'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"COMPLEX","CLASS","NULL","EXTENDS","THIS","SUPER","WHILE","FOR","IF","ELSE",
"RETURN","BREAK","NEW","CASE","DEFAULT","PRINT","READ_INTEGER","READ_LINE",
"PRINTCOMP","LITERAL","IDENTIFIER","AND","OR","STATIC","INSTANCEOF",
"LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","DCOPY","SCOPY","DO","OD",
"DOSEPERATOR","UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : COMPLEX",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : PrintCompStmt ';'",
"Stmt : DoStmt ';'",
"DoStmt : DO DoBranchList DoSubStmt OD",
"DoBranchList : DoBranchList DoSubStmt DOSEPERATOR",
"DoBranchList :",
"DoSubStmt : Expr ':' Stmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"DefaultExpr : DEFAULT ':' Expr ';'",
"CaseExprList : CaseExprList Constant ':' Expr ';'",
"CaseExprList :",
"Expr : DCOPY '(' Expr ')'",
"Expr : SCOPY '(' Expr ')'",
"Expr : SUPER",
"Expr : CASE '(' Expr ')' '{' CaseExprList DefaultExpr '}'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : '@' Expr",
"Expr : '$' Expr",
"Expr : '#' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
};

//#line 516 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 707 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 57 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 63 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 67 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 77 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 83 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 87 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 91 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 95 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 99 "Parser.y"
{
				yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
			}
break;
case 11:
//#line 103 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 107 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 113 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 119 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 123 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 129 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 133 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 137 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 145 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 152 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 156 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 163 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 167 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 173 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 179 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 183 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 190 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 195 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 212 "Parser.y"
{
				yyval.stmt = new Tree.DoOdLoop(val_peek(2).doExprList, val_peek(2).doStmtList, val_peek(1).doExpr, val_peek(1).doStmt, val_peek(3).loc);
			}
break;
case 40:
//#line 218 "Parser.y"
{
				yyval.doExprList.add(val_peek(1).doExpr);
				yyval.doStmtList.add(val_peek(1).doStmt);
			}
break;
case 41:
//#line 223 "Parser.y"
{
				yyval = new SemValue();
				yyval.doExprList = new ArrayList<Expr>();
				yyval.doStmtList = new ArrayList<Tree>();
			}
break;
case 42:
//#line 231 "Parser.y"
{
				yyval.doExpr = val_peek(2).expr;
				yyval.doStmt = val_peek(0).stmt;
			}
break;
case 43:
//#line 238 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 44:
//#line 242 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 45:
//#line 246 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 47:
//#line 253 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 48:
//#line 259 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 49:
//#line 266 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 50:
//#line 272 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 51:
//#line 281 "Parser.y"
{
				yyval.expr = val_peek(1).expr;
			}
break;
case 52:
//#line 288 "Parser.y"
{
				yyval.caseConstList.add(val_peek(3).expr);
				yyval.caseExprList.add(val_peek(1).expr);
			}
break;
case 53:
//#line 293 "Parser.y"
{
				yyval = new SemValue();
				yyval.caseConstList = new ArrayList<Expr>();
				yyval.caseExprList = new ArrayList<Expr>();
			}
break;
case 54:
//#line 301 "Parser.y"
{
				yyval.expr = new Tree.DCopyExpr(val_peek(1).expr, val_peek(3).loc);
			}
break;
case 55:
//#line 305 "Parser.y"
{
				yyval.expr = new Tree.SCopyExpr(val_peek(1).expr, val_peek(3).loc);
			}
break;
case 56:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.SuperExpr(val_peek(0).loc);
			}
break;
case 57:
//#line 313 "Parser.y"
{
				yyval.expr = new Tree.Case(
						val_peek(5).expr, val_peek(2).caseConstList, val_peek(2).caseExprList, val_peek(1).expr, val_peek(7).loc
					);
			}
break;
case 58:
//#line 319 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 61:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 353 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 357 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 361 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 365 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 369 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 373 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 74:
//#line 377 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 75:
//#line 381 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 76:
//#line 385 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 77:
//#line 389 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPRE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 78:
//#line 393 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPIM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 79:
//#line 397 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.INT2COMP, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 80:
//#line 401 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 81:
//#line 405 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 82:
//#line 409 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 83:
//#line 413 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 84:
//#line 417 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 85:
//#line 421 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 86:
//#line 425 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 87:
//#line 431 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 90:
//#line 442 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 91:
//#line 449 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 92:
//#line 453 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 93:
//#line 460 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 94:
//#line 466 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 95:
//#line 472 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 96:
//#line 478 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 97:
//#line 484 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 98:
//#line 488 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 99:
//#line 494 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 100:
//#line 498 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 101:
//#line 504 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 102:
//#line 510 "Parser.y"
{
				yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
			}
break;
//#line 1399 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
