JAVAC       = javac
SOURCES     = $(wildcard  src/main/java/**/**.java)
SRCDIR      = src/main/java
OUTDIR      = bin/
CLIENT_MAINCLASS   = ConnectFour
SERVER_MAINCLASS   = ConnectFourServer
MAINFILE    = $(SRCDIR)/$(CLIENT_MAINCLASS).java $(SRCDIR)/$(SERVER_MAINCLASS).java
HOST        = localhost
PORT        = 5678
PLAYER1NAME = Fuad
PLAYER2NAME = Brenda

server: classes
	cd bin; java $(SERVER_MAINCLASS) $(HOST) $(PORT)
client1: classes
	cd bin; java $(CLIENT_MAINCLASS) $(HOST) $(PORT) $(PLAYER1NAME)
client2: classes
	cd bin; java $(CLIENT_MAINCLASS) $(HOST) $(PORT) $(PLAYER2NAME)
classes: clean outdir
	$(JAVAC) -d $(OUTDIR) -sourcepath $(SRCDIR) -Xlint $(MAINFILE)
outdir:
	mkdir $(OUTDIR)
clean:
	rm -rf $(OUTDIR)
old_server:
	java -cp lib/server.jar ConnectFourServer $(HOST) $(PORT)
