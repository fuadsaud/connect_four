JAVAC       = javac
SOURCES     = $(wildcard  src/main/java/**/**.java)
SRCDIR      = src/main/java
OUTDIR      = bin/
MAINCLASS   = ConnectFour
MAINFILE    = $(SRCDIR)/$(MAINCLASS).java
HOST        = localhost
PORT        = 5678
PLAYER1NAME = Fuad
PLAYER2NAME = Brenda

client1: classes
	cd bin; java $(MAINCLASS) $(HOST) $(PORT) $(PLAYER1NAME)
client2: classes
	cd bin; java $(MAINCLASS) $(HOST) $(PORT) $(PLAYER2NAME)
classes: clean outdir
	$(JAVAC) -d $(OUTDIR) -sourcepath $(SRCDIR) -Xlint $(MAINFILE)
outdir:
	mkdir $(OUTDIR)
clean:
	rm -rf $(OUTDIR)
server:
	java -cp lib/server.jar ConnectFourServer $(HOST) $(PORT)
