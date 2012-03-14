import java.io.*;


public class PhosphoStats {

	public PhosphoScore.Score[] Scores;
	String[] scan;
	int[] charge;
	String[] protref;
	private double Zmean = 0;
	private double Zstd = 0;
	private double Dmean = 0;
	private double Cmean = 0;
	private double Dstd = 0;
	private double DDmean = 0;
	private double DDstd = 0;
	private double Cstd = 0;
	private double PercPass = 0;
	private int count, true_count;
	
	public PhosphoStats(int size) {
		Scores = new PhosphoScore.Score[size];
		scan = new String[size];
		charge = new int[size];
		protref = new String[size];
		count = 0;
		true_count = 0;
	}
	
	public void add(String scan_t, int charge_t, String protref_t, PhosphoScore.Score S) {
		Scores[count] = S;
		scan[count] = scan_t;
		protref[count] = protref_t;
		charge[count++] = charge_t;		
		if (S.Ecount > 1) {
			true_count++;
		}
	}
	
	public void PrintStats(PrintWriter outwriter) {
		
		int index;
		String ambig;
		
		for (int i=0; i<count; i++) {
			if (Scores[i].Ecount > 1) {
				Zmean += Scores[i].zscore;
				Dmean += Scores[i].diff;
				DDmean += Scores[i].dcdistance;
				Cmean += Scores[i].cost[0];
			}
		}
		Zmean /= true_count;
		Dmean /= true_count;
		DDmean /= true_count;
		Cmean /= true_count;
		for (int i=0; i<count; i++) {
			if (Scores[i].Ecount > 1) {
				Zstd += (Zmean - Scores[i].zscore)*(Zmean - Scores[i].zscore);
				Dstd += (Dmean - Scores[i].diff)*(Dmean - Scores[i].diff);
				DDstd += (DDmean - Scores[i].dcdistance)*(DDmean - Scores[i].dcdistance);
				Cstd += (Cmean - Scores[i].cost[0])*(Cmean - Scores[i].cost[0]);
			}
		}
		Zstd = java.lang.Math.sqrt(Zstd/true_count);
		Dstd = java.lang.Math.sqrt(Dstd/true_count);
		DDstd = java.lang.Math.sqrt(DDstd/true_count);
		Cstd = java.lang.Math.sqrt(Cstd/true_count);
		
		for (int i=0; i<count; i++) {
			index = scan[i].lastIndexOf(File.separatorChar);
			index = index < 0 ? 0 : index+1;
			ambig = "Passed    ";
			
			for (int k=0; k < Globals.CONFMODE.length && Globals.CONFMODE[k] != null; k++) {
				if (Scores[i].Ecount == 1) {
					ambig = "OneChoice  ";
				} else if (Globals.CONFMODE[k] == Globals.CONF.DSCORE) {
					ambig = (Scores[i].diff < Globals.CONFCUT[k]) ? "Ambiguous" : ambig; 
				} else if (Globals.CONFMODE[k] == Globals.CONF.ZSCORE) { 
					ambig = (Scores[i].zscore > Globals.CONFCUT[k]) ? "Ambiguous" : ambig;
				} else {
					ambig = (Scores[i].dcdistance > Globals.CONFCUT[k]) ? "Ambiguous" : ambig;
				}
			}
			
			if (ambig.matches("Ambiguous")) {
				PercPass++;
			}
			
			index = (scan[i].length()-34 < index) ? index : scan[i].length()-34;
			outwriter.printf("%-30s\t%d\t% 1.5f\t%1.5f\t\t%1.5f\t%s\t%s\t%s\n", scan[i].substring(index, scan[i].length()-4), 
					charge[i], Scores[i].zscore, Scores[i].dcdistance, Scores[i].diff, 
					/*Scores[i].cost[0], Scores[i].cost[1], Scores[i].nodes[0], Scores[i].nodes[1],*/ ambig, Scores[i].peptide.toString(),
					protref[i]);	
		}
		
		outwriter.printf("\n----Run Statistics----\n");
		outwriter.printf("Zscore Mean: %1.5f\tZscore Std: %1.5f\n", Zmean, Zstd);
		outwriter.printf("DCdistance Mean: %1.5f\tDCdistance Std: %1.5f\n", DDmean, DDstd);
		outwriter.printf("Dscore Mean: %1.5f\tDscore Std: %1.5f\n", Dmean, Dstd);
		outwriter.printf("Percent Passed Peptides: %1.5f\n", 1-PercPass/count);
		//outwriter.printf("Cost Mean: %1.5f\tCost Std: %1.5f\n", Cmean, Cstd);
		
		outwriter.flush();
		
			
	}
	
	public double Zmean() {
		return this.Zmean;
	}
	
	public double Dmean() {
		return this.Dmean;
	}
	
	public double DDmean() {
		return this.DDmean;
	}
	
	public double PercPass() {
		return 1-this.PercPass/count;
	}
	
}
