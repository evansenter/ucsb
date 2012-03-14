-------------------------------------------------------------------------------
01-08-08
PhosphoScore:  Phosphorylation Site Determination for MSn-Based Proteomics

Developed at the LKEM, NHLBI, NIH
Contact Brian Ruttenberg (berutten@cs.ucsb.edu, ruttenberg@gmail.com) or
Dr Jason Hoffert (hoffertj@nhlbi.nih.gov) for more information

-------------------------------------------------------------------------------

Table of Contents

1. Introduction
2. Quick Start Instructions
3. PhosphoGUI Instructions
4. Command Line Usage
	a. PhosphoScore
	b. PhosphoGibbs
5. Output Files Description
6. Brief Algorithm Description
7. Limitations/Programming Notes
8. Troubleshooting/Common Problems
9. Additional Information

-------------------------------------------------------------------------------

1.  Introduction

	PhosphoScore is a standalone java application intented to assign 
phosphorylation sites in peptides derived from tandem Mass Spec
experiments.  It is intended to be used on a complete data set of Phosphorylated 
peptides from any MS level MS2 or higher.  The PhosphoScore algorithm uses a combination
of peptide matching and ion intensity to determine the most likely 
phosphorylation site for a given protein.  It is extrememly flexible, enabling
the user to change the algorithm parameters for different experiments.  In 
addition, it implements a parameter optimization algorithm for new data sets
where the optimal parameters are unknown.
	PhosphoScore can be run through the PhosphoGUI graphical interface, 
or through the command line.  It has been tested on Windows and Mac OSX.


-------------------------------------------------------------------------------

2.  Quick Start Instructions

	If you hate reading, are in a hurry, or are just impatient like the 
author, you can quickly get started by following the instructions below.  These
instructions are intended for someone who is using this program for the first
time with a new or unanalyzed data set.
(instructions are for windows only at the moment)


!!!FIRST THING'S FIRST!!!

Go to the PhosphoScore Folder and rename the PhosphoScore.bat.txt to PhosphoScore.bat

	1.  Load up the GUI

	Double click on the .bat file named PhosphoScore.bat.  You can also
	run by double clicking on PhosphoScore.jar, but you will not be able to 
	see some errors, so it is recommended you use the .bat until you
	are comfortable with the program.

	2.  Enter the input directory

	Enter the directory that contains all the .dta, .out and sequest.params
	files for the peptides you want to analyze.  If you do not use
	Sequest (and hence your data is in a different format), see the
	programming notes.  For the first time you run, I recommend data sets
	of no more than 100 peptides until you can see the speed of your system.

	3.  Enter the output directory

	This is where all the output files will be written to.

	4.  Click on the Defaults button

	5.  Click Run.

And thats it.  If you have any errors, a dialog box will pop up OR they will 
appear in the command window (so be sure to check there!). A progress bar should
pop up if everything is ok.  Your data should be done after it has performed
all the iterations.  Depending on the data set size and peptide phosphorylation
, this can take anywhere from 5 minutes to several hours, which is why I
recommend you start with a small data set. 

When its done, your results will be in your specified output directory, in the
files called GibbsResult.txt and PhosphoScoreGibbs.txt.  Recoverable errors 
will be in PhosphoScoreGibbs.err.  For an explanation of these output files, 
see section 5.

-------------------------------------------------------------------------------

3.  PhosphoGUI Instructions


PhosphoGUI is a graphical interface to run the two core programs, PhosphoScore
and PhosphoGibbs.  A description of those command line programs are in the
next section.

	1.  Running the GUI.

	The GUI is contained within a single java .jar file.  There are
	several ways to start it up.  If you are on a PC, it is recommended
	that you use the .bat to start up the GUI.  This is because certain
	critical errors are output to the command line.  If you are on a Mac,
	you can just type the contents of the .bat file into a terminal window.
	When you are more comfortable with the program, it can also be run 
	directly by double clicking on the .jar file.  Note: Some machines
	with older versions of java have had trouble running the .jar directly.

	You can also unpack the .jar which contains the .class and .java files,
	and run directly off of those

	2.  GUI options explanation

	**** Input/Output Directory

	The directories for the input and output should be placed here.
	The input directory should be a directory that contains the .out, 
	.dta and a sequest.params file for all the peptides you wish to 
	anaylze.  The program will anaylyze ALL peptides in that directory.
	If you use something besides Sequest, see the programming notes section
	The program is pretty sensitive to the .out and sequest.params file
	format, so this is a common place for errors to pop up.  

	The output directory is just the directory to place all the output
	files.  If this is left blank, the current directory will be used.
	You may also type a filename after the output directory, and the 
	output of the program will go that file instead.  Otherwise, 
	the output will be placed in PhosphoScoreGibbs.txt, GibbsResult.txt,
	or PhosphoScore.txt

	**** Program Type

	This selects which program you want to run.  PhosphoScore is intended
	to be run on a dataset in which the parameters have already been 
	determined.  PhosphoGibbs is intended to use Gibbs sampling to 
	determine the optimal parameters for a data set.  

	**** Parameters

	These are the parameters than can be varied for PhosphoScore. If 
	PhosphoScore is being run, only the values in the first column ("Min")
	of each parameter will be used.  If PhosphoGibbs is being run, all
	three columns of each parameter are used ("Min, Max and Step").  If the
	first column is left blank or "Min", that parameter is ignored and the 
	program default is used.If PhosphoGibbs is being run, the Min, Max and Step columns determine the 
	parameter space to iterate over.  Blank parameters are not iterated
	at all.  If you want to change the program default but don't want 
	that parameter iterated, you can set the Min, Max and Step columns to
	be all the same value.  Ie, setting MATCHTOL to .9, .9, .9 means that
	the match tolerance is set to .9, overriding the program default, but
	Gibbs does not iterate over that parameter.

	The TOTALWEIGHT parameter cannot be used when running PhosphoScore, it is 
	only used for PhosphoGibbs.  If the TOTALWEIGHT parameter is used, the
	INTWEIGHT and MATCHWEIGHT parameters need to be left as "Min" (ie, don't
	iterate over those two parameters).  The TOTALWEIGHT parameter will randomly
	pick a value for MATCHWEIGHT between the Min and Max, and set the 
	INTWEIGHT equal to TOTALWEIGHT.Max - MATCHWEIGHT (so INTWEIGHT and
	MATCHWEIGHT always add up to TOTALWEIGHT's max)

	INTMODE can be set to either "RelativeRank" or "RelativeIntensity"
	depending on whether you want each peak in a spectrum to be rank ordered based on the
	number of peaks or to have the	normalized intensities enter into the cost calculation.
	RelativeRank is the default mode and performs well on a variety of data sets.

	MATCH TOLERANCE (Standard Deviation) - Default is 1000 ppm which corresponds to the mass 
	accuracy of an LTQ mass spectrometer. Can be varied depending on the MS used for analysis.

	See the sections on the algorithm description or the paper for an
	explanation of what each parameter does.

	**** Confidence Cutoffs

	These boxes let the user choose the cutoff values that label a peptide
	as ambigous or not.  The values are ANDed together, meaning if you enter
	a value in the Dscore box and Zscore box, a peptide must meet both of 
	those cutoffs before it is labeled as unambiguous.  Dscore is just a 
	measure of how close the best phosphorylation assignment is to the
	second best phosphorylation assignment.  Zscore is a measure
	of how the best phosphorylation assignemnt is compared to the space of
	all possible phosphorylation assignments.  DCdistance is a measure of
	how close the average amino acid cost is to the default cost

	All the values are "greater than" values, meaning a peptide with a
	Dscore or Zscore greater than the cutoff is labeled as
	passed/unambiguous.  

	Confidence cutoffs may need to be experimentally derived or tweaked a bit depending
	how your results come out.  We used a Dscore threshold of 1% for our datasets.

	**** Gibbs Sampling Options

	These options are only for use with gibbs sampling.  The optimization 
	criteria detemines which indicators Gibbs sampling should try to 
	minimize/maximize. These are also ANDed values, so two boxes checked
	means that Gibbs must satisfy both conditions.

	For a more detailed explanation, see the PhosphoGibbs section.  But
	the gist is that PhosphoGibbs will try to minimize/maximize the average
	Dscore/Zscore/DCdistance for a group to determine the optimal parameters
	for this data set. This is done because from experiments with positive
	control there exists a strong correlation between the average Dscore/
	Zscore/DCdistance of a data set and positive identification.

	The settings are as follows:
	Dscore, DCdistance checked (one only) - will minimize Dscore/DCdistance
	Zscore checked - will maximize Zscore
	Dscore AND DCdistance checked - will minimize DCdistance and maximize Dscore

	Minimum Sensitivity: This value (0 - 1) sets a lower bound for the percentage of 
	unambiguous identifications. PhosphoGibbs will optimize the parameters of
	the cost function in order to minimize the DC distance of the data set to 
	achieve this target sensitivity.

	The termination criteria detemines when the gibbs sampling should stop.
	If Max iterations is selected, gibbs runs for the number of iterations
	specified in the box.  If No Change Iterations is selected, gibbs runs
	until it has completed the specified number of iterations in which the
	minimum/maximum value has not changed.

	**** Buttons

	The run button runs the selected program, the defaults button loads
	common defaults.  If PhoshoScore is selected and the defaults 
	button is pressed, PhosphoScore defaults will be loaded instead of
	PhosphoGibbs.  Clear clears the values, help brings up this readme.

-------------------------------------------------------------------------------

4.  Command Line Usage

PhosphoScore and PhosphoGibbs can be run at the command line by typing:

java -cp PhosphoScore.jar <PhosphoGibbs|PhosphoScore> <options>

Running without any options will bring up the program usage.  The format for
the option usage is explained there.  For a detailed explanation on how each
parameter will affect the results, 


-------------------------------------------------------------------------------


5.  Output File Description

PhosphoScore generates two main output files.  PhosphoScore.out and
PhosphoScore.err are generated (if not output file is given). PhosphoScore.err
contains errors for peptides that could not be run using PhosphoScore, such as
peptides with 0 or 1 phosphorylation sites, too many phosphorylation sites (13
is the max) or various other reasons.  These errors are only per peptide and
the rest of the data set can run.  PhosphoScore.out contains the results of
non-error peptides.  The result is of this format for each peptide:

<File name>  <charge>  <Zscore>  <DCdistance>  <Dscore>  <Confidence>  <Sequence>

The Zscore/DCdistance/Dscore are all values derived from the the calculation of
each peptide.  Confidence displayed passed or ambiguous according to the 
confidence parameters specified at run time.  And the peptide sequence
contains the phosphorylation sites the program determined for this peptide.

In addition, some statistics about the whole data set are printed at the 
bottom.

PhosphoGibbs produces three output files, GibbsResult.txt, PhosphoScoreGibbs.txt
and PhosphoScoreGibbs.err. GibbsResult.txt show detailed information about
each iteration of the gibbs algorithm and how it converged on the 
final parameters.  The final optimal parameters that it found are printed at 
the bottom.  The PhosphoScoreGibbs.txt and PhosphoScoreGibbs.err are the
results from PhosphoScore when running with the optimal parameters 
determined from PhosphoGibbs (ie, it runs PhosphoScore one last time with
the parameters at the bottom of the GibbsResults.txt file).


-------------------------------------------------------------------------------

6.  Short Algorithm Description


**** PhosphoScore ****

	PhosphpoScore works by constructing a tree of all possible
phosphorylation sites using a cost function.  The path through the tree from 
the root to the leaves with the least cost is determined to be the most likely
phosphorylation.  At each possible site, the cost is calculated (using a cost
function described below) that determines how much it would cost to phosphorylate
or not phosphorylate that specfic site.  The cost is determined using this 
equation:


IF (RealPeak within MATCHTOL of TheoreticalPeak) AND 
	(IntensityRank > THRESHOLD)
{

Cost = MATCHWEIGHT *
	// Area of the square from the mean to the observed
	((2 *   normalPdf(MATCHTOL/2)* abs(RealPeak-TheoreticalPea)) -
	// Area under the curve
	(2 *	abs(.5 - ProbMath.normalCdf(RealPeak-TheoreticalPeak, MATCHTOL))))
	+
       INTWEIGHT * (1-IntensityRank)^INTEXP
}
ELSE 
{
Cost = DEFAULTCOST
}
  
Where:
	RealPeak = m/z of a peak in the spectrum
	TheoreticalPeak = m/z of where the peak should be given the current
		phosphorylation of this site
	normalCDF = The normal CDF of a normal curve with a mean of the
		theoretical peak and a standard deviation of MATCHTOL/2.
	normalPDF = The probability of the mean of a normal with a standard
			devialtion of MATCHTOL

In other words - the closer a spectrum's peak m/z is to the theoretical 
peaks m/z and the higher it's intensity is relative to the other peaks, 
the lower cost is to phosphorylate this site.  

The tree is calculated for all possible phosphorylation configurations
that meet the tolerance of the parent mass.  Ie, if there are 3 possible
phosphorylation sites but the mass of the parent ion only indicates 2 are
phosphorylated, no leaf with all 3 sites phosphorylation will be generated.

When the tree is complete, the leaf with the loweest cost through the tree
is chosen as the most likely phosphorylation sites.  Note, when dealing with 
different ions and charge states, all the information is aggregated into 
one final tree.  Ie, a tree is computed for y and b ions, and each charge state
and then added together, then the shortest path is chosen.  There is an
option to change how the trees are combined, but its use is not recommended.

The Dscore and Zscore are then computed.  The Dscore is merely:

(CostofBestPath-CostofSecondBestPath)/CostOfBestPath

The Zscore is computed by:

(CostofBestPath - MeanOfAllPaths)/StdDevOfAllPaths

The DCdistance is computed by:

DefaultCost - CostofBestPath/# of Amino Acids	



**** PhosphoGibbs ****

Because the cost function contains several parameters that can different
for various experiments, PhosphoGibbs is a program designed to find the
optimal parameters for a given data set.  For example, for very noisy
mass spec runs, the MATCHTOL might need to be reduced so noisy peaks
are not misinterpreted as real peaks, or for MS3 and MS4, the INTWEIGHT
may need to be reduced since the Signal/Noise is reduced.

Gibbs sampling works by randomly exploring the space of all possible values 
for each parameter, and incrementally converging parameters that improve
the result.  There are three options to use as the result - Dscore, Zscore
and DCdistance

From experiments with positive control, the number of correct
peptides that PhosphoScore predicted in the data set increased as the 
average Dscore and Zscore of the data set decreased.  Therefore for 
real data sets (in which we don't know what is correct or not), we can
have PhosphoGibbs try to minimize the Dscore/Zscore of the data set, 
which (usually) improves the quality of the results.

PhosphoGibbs starts by randomly choosing values for each parameter that is
specified to be iterated (not all parameters need to be iterated generally).
The defaults of the GUI contain what we have found is most common that you
need to iterate.  It then picks one parameter and randomly chooses a new 
value for it. Then it runs PhosphoScore on the entire data set and
calculates the average Dscore or Zscore (depends on the option you specify).
If the Dscore/Zscore is lower than the previous best Zscore/Dscore, the 
new parameter is kept.  Otherwise it reverts to the old parameter values.
Then a random value is chosen for the next parameter, and repeated.

The algorithm stops on one of two conditions.  If max iterations is set, 
Gibbs stops when it reaches the specified number of iterations.  If no change
iterations is set, then Gibbs iterates until it finds N consecutive iterations
where new parameters are not taken (where the user specifies N).


-------------------------------------------------------------------------------

7. Limitations/Programming Notes

PhosphoGUI was programmed in the spirit of being flexible and portable to 
different systems, labs and users.  However, spirit alone can't actually 
write code, so unfortunately there are some limitations.

	1. Run Times

	For reasonable data sets, the program runs in a reasonable amount of 
	time.  However, since the tree building algorithm is (nearly) exponential
	peptides with 10-13 phosphorylation sites can take a LONG time.  If you
	have many of these, I recommend you take them out when running 
	PhosphoGibbs, and once you have found the optimal paramters put it 
	back in for a final run with PhosphoScore.  You can also go into 
	the Globals.class file and lower the MAXIONS variable.  In that case
	it skips any peptide with more than MAXIONS phospho sites (and 
	remarks in the .err file that it skipped those) and you can manually
	look at those few.  Or you can reduce the size of your data set or
	reduce the GIbbs iterations.  100 is the default but it is rare that
	it takes that many to converge.

	2. Mass Spec Data

	The program only works for Sequest searches where the data is in 
	.out and .dta files. The program is a little bit sensitive to the 
	formats of the .out files, but it has worked for several versions
	of sequest outfiles.  The program is however, flexible that if a user
	wanted to run PhosphoScore with another interface, it is easy to plug
	it in.  The Sequest specific code is in one class caled SequestInterface.
	The user can implement their own interface class that gets the mass 
	spec data however, and all they need to do is to make sure to implement
	a few key functions and the rest of the program should work fine.
	See the SequestInterface.java for details.

	3. Phosphorylation Modifications

	Right now atomic weight modifications are fixed for phosphorylated
	peptides.  ST can be -18, and STY can be +80.  Should this need to 
	change, you would need to program it in.  Note that MS2 or MS3+
	has no affect on these numbers, since the atomic weights of the 
	parent ions in each case will be unique (ie, no way to assign a
	site as -18 in MS2).

	4.  Other non-Phosphorylation Modifications

	The program only handles an m/z modification on Methionine ('M'),
	though again support is there to add more.  There is a section in 
	SequestInterface.class that determines if there are any of these
	other non-Phosphorylation modifications and adjusts for it 
	appropriately.  If you need to add another one, look in the
	SequestInterface.class for the section that mentions how to add
	a new modificaton.  Beware though, there still may be some left
	over code sections that might break if you add more.

	5. Some fixed values

	Some values/variables/settings are hardcoded into the program if we
	determined that we could reasonably put a value there that most 
	likely would never be reached.  You might want to look through the
	Globals.java file to see some of the hardcoded constants. For example,
	the maximum number of phosphorylation sites is 13, since any more
	will increase the size of the tree more than the precision of java
	integers.

In any case, if you come across any of these limitations or are considering 
modifying the code, its probably best to just contact the authors.


-------------------------------------------------------------------------------

8. Troubleshooting/Common Problems

Some things you can try:

	1. Run the program/GUI from the command line.  Some important errors
	are spit out on the command line, so if you run directly from the .jar
	you may not see some errors.

	2. Try reducing your data set size to start if you are having problems.

	3. Large data sets do not usually have memory problems, but a peptide
	with 10-12 possible phosphorylation can eat up all your memory.  If you
	run out of memory, try running from the command line with the option
	-Xs512mb.

	4. Make sure you check the .err file to see if it didn't like any
	peptides.

	5. Realtime scanning anti-virus software may cause PhosphoScore to run 
	very slowly or freeze. If this happens, disable realtime scanning and the
	software should run normally.

	
All else fails, contact the authors. 



-------------------------------------------------------------------------------

9.  Additional Information



See paper: TBD





