date = $(shell git log | grep -m 1 Date | sed -r 's/Date: +[A-Z][a-z]+ ([A-Z][a-z]+) ([0-9]+) [^ ]+ ([0-9]+) .+/\2_\1_\3/')
version = $(shell git describe)

all: bioinformer.pdf

bioinformer.pdf: bioinformer.tex preface.tex stringMatching.tex suffixTree.tex repeat.tex hashTable.tex dotPlot.tex substitutionMatrices.tex numAlignments.tex alignment.tex coalescent.tex phylogeny.tex hmm.tex t11.1 ref.bib
	echo $(date) | tr '_' ' ' > date.txt
	echo $(version) | tr '-' ' ' | awk '{printf "%s", $$1; if ($$2) printf "-%s", $$2; printf "\n"}' > version.txt
	latex bioinformer
	bibtex bioinformer
	latex bioinformer
	latex bioinformer
	dvips bioinformer -o -q
	ps2pdf -dALLOWPSTRANSPARENCY bioinformer.ps
publish: bioinformer.pdf
	if [ -d /mnt/c/Users/haubold/ownCloud\ -\ haubold@evolbio.mpg.de@owncloud.gwdg.de/docs/ ]; then \
                cp bioinformer.pdf /mnt/c/Users/haubold/ownCloud\ -\ haubold@evolbio.mpg.de@owncloud.gwdg.de/docs/; \
	fi
clean:
	rm -f bioinformer.aux bioinformer.bbl bioinformer.blg bioinformer.dvi bioinformer.log bioinformer.pdf bioinformer.ps bioinformer.toc
