
import java.util.Arrays;

/* Class of a MS spectrum
 * 
 * 
 * 
 */


public class Spectrum {

	private Peak peaks[];
	private int length;
		
	static class Peak implements Comparable<Object> {
		public double mz;
		public double intensity;
		
		Peak(double m, double i) {
			mz = m;
			intensity = i;
		}
		
		public int compareTo(Object P) throws ClassCastException {
			if (!(P instanceof Peak)) { 
				throw new ClassCastException("A Peak object expected.");
			}
			double intensity_t = ((Peak) P).intensity; 
			if (intensity - intensity_t > 0) {
				return 1;
			} else if (intensity - intensity_t < 0) {
				return -1;
			} else {
				return 0;
			}
		}
		
	}
	
	
	// Reads a MS Spectrum from a turboSEQUEST .dta file
	public Spectrum(Peak[] in_peaks) {
		peaks = in_peaks;
		length = peaks.length;
	}
	 
	public int length() {
		return length;
	}
	public double mz(int i) {
		try {
			return peaks[i].mz;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			Globals.SysError("Error:  M/Z index out of range: " + String.valueOf(i) +
						" out of " + peaks.length);
			return 0;
		}
	}
	public double intensity(int i) {
		try {
			return peaks[i].intensity;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			Globals.SysError("Error:  Intensity Data not in the spectrum");
			return 0;
		}
	}
	
	public double max() {
		return peaks[peaks.length-1].intensity;
	}
	
	public int max_index() {
		return peaks.length-1;
	}
	
	// Override a value in the file and set an m/z manually
	public void set(int mz_t, double int_t) {
		try {
			peaks[mz_t].mz = int_t;
			Arrays.sort(peaks);
		} catch (Exception e) {
			Globals.SysError("Error:  Unable to set spectrum");
		}
		
		
	}
	
}
