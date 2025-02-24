all: dist/bioinformer.zip

dist/bioinformer.zip: lib/gnujaxp.jar lib/jcommon-1.0.0-rc1.jar lib/jfreechart-1.0.0-rc1.jar lib/junit.jar lib/licence-LGPL.txt lib/maths.jar lib/sequencetools.jar lib/servlet.jar src res
	ant
	mkdir -p bioinformer
	mv dist/bioinformer.jar bioinformer
	cp -r lib bioinformer
	zip -r bioinformer.zip bioinformer
	mv bioinformer.zip dist
	rm -r bioinformer
lib/gnujaxp.jar:
	wget https://owncloud.gwdg.de/index.php/s/B9uuasvkrn3THkd/download -O lib/gnujaxp.jar
lib/jcommon-1.0.0-rc1.jar:
	wget https://owncloud.gwdg.de/index.php/s/4oBVUgRA4CVHW8v/download -O lib/jcommon-1.0.0-rc1.jar
lib/jfreechart-1.0.0-rc1.jar:
	wget https://owncloud.gwdg.de/index.php/s/bfseVrX3IhMxPod/download -O lib/jfreechart-1.0.0-rc1.jar
lib/junit.jar:
	wget https://owncloud.gwdg.de/index.php/s/KdAeRN5AuxKUcWn/download -O lib/junit.jar
lib/maths.jar:
	wget https://owncloud.gwdg.de/index.php/s/Lef5rxup0YAEnRc/download -O lib/maths.jar
lib/sequencetools.jar:
	wget https://owncloud.gwdg.de/index.php/s/mV0mzylO4fION4o/download -O lib/sequencetools.jar
lib/servlet.jar:
	wget https://owncloud.gwdg.de/index.php/s/IeDCwMFIpwYetga/download -O lib/servlet.jar
publish: dist/bioinformer.zip
	if [ -d /mnt/c/Users/haubold/ownCloud\ -\ haubold@evolbio.mpg.de@owncloud.gwdg.de/docs/ ]; then \
                cp dist/bioinformer.zip /mnt/c/Users/haubold/ownCloud\ -\ haubold@evolbio.mpg.de@owncloud.gwdg.de/jars/bioinformer/; \
	fi
clean:
	rm -f lib/*.jar dist/bioinformer.zip bioinformer
	ant clean
