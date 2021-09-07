# CharsToArray
Replaces Multi-Char rows of exported Simatic S5-DB´s to an importable Simatic-S7 DB.

Basically, if you export an DB from an old Simatic-S5, the .db File´s Chars will look like this in a Texteditor:
```
DATA_BLOCK DB50
VERSION : 0.1
   STRUCT 
	  [...]
	  D_31 : Array[0..9] of Char;   // Comment
	  [...]
   END_STRUCT;

BEGIN
   D_31 := '0000000000';
END_DATA_BLOCK
```

Sadly, if you want to import this into a Simatic-S7, the Software will change these Arrays of Chars to Strings, as the Chars-defaults are declared in one Row. But if you convert these into Strings, they´ll user another Offset (+2 Bytes per String), and this may cause Problems.

So what this Software does, it just scans the whole File, and replaces these Char-Arrays with S7-Readable Char-Arrays, which will look like this:
```
DATA_BLOCK DB50
VERSION : 0.1
   STRUCT 
	  [...]
	  D_31 : Array[0..9] of Char;   // Comment
	  [...]
   END_STRUCT;

BEGIN
   D_31[0] := '0';
   D_31[1] := '0';
   D_31[2] := '0';
   D_31[3] := '0';
   D_31[4] := '0';
   D_31[5] := '0';
   D_31[6] := '0';
   D_31[7] := '0';
   D_31[8] := '0';
   D_31[9] := '0';
END_DATA_BLOCK
```
Upon first running, the .jar creates a config.json File, where you can determine the Name of the Input- and Outputfile as well as a MaxSize for Arrays (if you want only Arrays of Chars up to x Elements converted), and a DebugMessage Option... which you´ll never use.

Maybe this is helping someone :)
