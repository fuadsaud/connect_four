JAVAC            = javac
SRCDIR           = src/main/java
SOURCES          = $(shell find $(SRCDIR) -type f -iname *.java)
OUTDIR           = bin/
CLIENT_MAINCLASS = ConnectFour
SERVER_MAINCLASS = ConnectFourServer
HOST             = localhost
PORT             = 5678
PLAYER1NAME      = Fuad
PLAYER2NAME      = Brenda

server: classes
	cd bin; java $(SERVER_MAINCLASS) $(HOST) $(PORT)
client1: classes
	cd bin; java $(CLIENT_MAINCLASS) $(HOST) $(PORT) localhost 5679 $(PLAYER1NAME)
client2: classes
	cd bin; java $(CLIENT_MAINCLASS) $(HOST) $(PORT) localhost 5680 $(PLAYER2NAME)
classes: clean outdir
	$(JAVAC) -d $(OUTDIR) $(SOURCES)
outdir:
	mkdir $(OUTDIR)
clean:
	rm -rf $(OUTDIR)
old_server:
	java -cp lib/server.jar ConnectFourServer $(HOST) $(PORT)
