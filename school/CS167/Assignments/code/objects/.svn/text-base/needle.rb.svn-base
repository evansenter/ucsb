# Evan Senter, CS167

class Needle
  # Currently using PAM250
  attr_accessor :gap_open, :gap_extension
  
  # Matrix used to generate the lookup hash:
  # [["A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V", "B", "Z", "X", "*"], [2, -2, 0, 0, -2, 0, 0, 1, -1, -1, -2, -1, -1, -3, 1, 1, 1, -6, -3, 0, 0, 0, 0, -8], [-2, 6, 0, -1, -4, 1, -1, -3, 2, -2, -3, 3, 0, -4, 0, 0, -1, 2, -4, -2, -1, 0, -1, -8], [0, 0, 2, 2, -4, 1, 1, 0, 2, -2, -3, 1, -2, -3, 0, 1, 0, -4, -2, -2, 2, 1, 0, -8], [0, -1, 2, 4, -5, 2, 3, 1, 1, -2, -4, 0, -3, -6, -1, 0, 0, -7, -4, -2, 3, 3, -1, -8], [-2, -4, -4, -5, 12, -5, -5, -3, -3, -2, -6, -5, -5, -4, -3, 0, -2, -8, 0, -2, -4, -5, -3, -8], [0, 1, 1, 2, -5, 4, 2, -1, 3, -2, -2, 1, -1, -5, 0, -1, -1, -5, -4, -2, 1, 3, -1, -8], [0, -1, 1, 3, -5, 2, 4, 0, 1, -2, -3, 0, -2, -5, -1, 0, 0, -7, -4, -2, 3, 3, -1, -8], [1, -3, 0, 1, -3, -1, 0, 5, -2, -3, -4, -2, -3, -5, 0, 1, 0, -7, -5, -1, 0, 0, -1, -8], [-1, 2, 2, 1, -3, 3, 1, -2, 6, -2, -2, 0, -2, -2, 0, -1, -1, -3, 0, -2, 1, 2, -1, -8], [-1, -2, -2, -2, -2, -2, -2, -3, -2, 5, 2, -2, 2, 1, -2, -1, 0, -5, -1, 4, -2, -2, -1, -8], [-2, -3, -3, -4, -6, -2, -3, -4, -2, 2, 6, -3, 4, 2, -3, -3, -2, -2, -1, 2, -3, -3, -1, -8], [-1, 3, 1, 0, -5, 1, 0, -2, 0, -2, -3, 5, 0, -5, -1, 0, 0, -3, -4, -2, 1, 0, -1, -8], [-1, 0, -2, -3, -5, -1, -2, -3, -2, 2, 4, 0, 6, 0, -2, -2, -1, -4, -2, 2, -2, -2, -1, -8], [-3, -4, -3, -6, -4, -5, -5, -5, -2, 1, 2, -5, 0, 9, -5, -3, -3, 0, 7, -1, -4, -5, -2, -8], [1, 0, 0, -1, -3, 0, -1, 0, 0, -2, -3, -1, -2, -5, 6, 1, 0, -6, -5, -1, -1, 0, -1, -8], [1, 0, 1, 0, 0, -1, 0, 1, -1, -1, -3, 0, -2, -3, 1, 2, 1, -2, -3, -1, 0, 0, 0, -8], [1, -1, 0, 0, -2, -1, 0, 0, -1, 0, -2, 0, -1, -3, 0, 1, 3, -5, -3, 0, 0, -1, 0, -8], [-6, 2, -4, -7, -8, -5, -7, -7, -3, -5, -2, -3, -4, 0, -6, -2, -5, 17, 0, -6, -5, -6, -4, -8], [-3, -4, -2, -4, 0, -4, -4, -5, 0, -1, -1, -4, -2, 7, -5, -3, -3, 0, 10, -2, -3, -4, -2, -8], [0, -2, -2, -2, -2, -2, -2, -1, -2, 4, 2, -2, 2, -1, -1, -1, 0, -6, -2, 4, -2, -2, -1, -8], [0, -1, 2, 3, -4, 1, 3, 0, 1, -2, -3, 1, -2, -4, -1, 0, 0, -5, -3, -2, 3, 2, -1, -8], [0, 0, 1, 3, -5, 3, 3, 0, 2, -2, -3, 0, -2, -5, 0, 0, -1, -6, -4, -2, 2, 3, -1, -8], [0, -1, 0, -1, -3, -1, -1, -1, -1, -1, -1, -1, -1, -2, -1, 0, 0, -4, -2, -1, -1, -1, -1, -8], [-8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, -8, 1]]
  PAIR_SCORES = { 
	      "ZN" => 1,  "KV" => -2, "DA" => 0,  "FY" => 7,  "PS" => 1,  "KW" => -3, "DB" => 3,  "FZ" => -5, "PT" => 0,  "ZP" => 0,  "B*" => -8, "KX" => -1, "DC" => -5, "ZQ" => 3,  "KY" => -4, "PV" => -1, 
        "IA" => -1, "ZR" => 0,  "KZ" => 0,  "DD" => 4,  "PW" => -6, "IB" => -2, "ZS" => 0,  "DE" => 3,  "G*" => -8, "PX" => -1, "IC" => -2, "ZT" => -1, "DF" => -6, "NA" => 0,  "PY" => -5, "ID" => -2, 
        "L*" => -8, "DG" => 1,  "NB" => 2,  "PZ" => 0,  "IE" => -2, "ZV" => -2, "DH" => 1,  "NC" => -4, "IF" => 1,  "ZW" => -6, "SA" => 1,  "DI" => -2, "ND" => 2,  "IG" => -3, "ZX" => -1, "SB" => 0, 
        "NE" => 1,  "Q*" => -8, "IH" => -2, "ZY" => -4, "SC" => 0,  "DK" => 0,  "NF" => -3, "XA" => 0,  "SD" => 0,  "DL" => -4, "NG" => 0,  "II" => 5,  "XB" => -1, "ZZ" => 3,  "V*" => -8, "DM" => -3, 
        "NH" => 2,  "SE" => 0,  "XC" => -3, "DN" => 2,  "NI" => -2, "IK" => -2, "SF" => -3, "XD" => -1, "IL" => 2,  "SG" => 1,  "XE" => -1, "DP" => -1, "NK" => 1,  "IM" => 2,  "SH" => -1, "XF" => -2, 
        "DQ" => 2,  "NL" => -3, "IN" => -2, "SI" => -1, "XG" => -1, "DR" => -1, "NM" => -2, "XH" => -1, "DS" => 0,  "IP" => -2, "SK" => 0,  "XI" => -1, "DT" => 0,  "NN" => 2,  "IQ" => -2, "SL" => -3, 
        "IR" => -2, "SM" => -2, "XK" => -1, "DV" => -2, "NP" => 0,  "IS" => -1, "SN" => 1,  "XL" => -1, "DW" => -7, "NQ" => 1,  "IT" => 0,  "XM" => -1, "NR" => 0,  "SP" => 1,  "DX" => -1, "XN" => 0, 
        "BA" => 0,  "NS" => 1,  "IV" => 4,  "SQ" => -1, "DY" => -4, "NT" => 0,  "IW" => -5, "SR" => 0,  "DZ" => 3,  "XP" => -1, "BB" => 3,  "IX" => -1, "XQ" => -1, "BC" => -4, "NV" => -2, "GA" => 1, 
        "IY" => -1, "SS" => 2,  "XR" => -1, "BD" => 3,  "NW" => -4, "GB" => 0,  "IZ" => -2, "ST" => 1,  "XS" => 0,  "BE" => 3,  "E*" => -8, "NX" => 0,  "GC" => -3, "XT" => 0,  "BF" => -4, "NY" => -2, 
        "GD" => 1,  "SV" => -1, "LA" => -2, "BG" => 0,  "NZ" => 1,  "GE" => 0,  "SW" => -2, "LB" => -3, "XV" => -1, "BH" => 1,  "GF" => -5, "SX" => 0,  "LC" => -6, "XW" => -4, "BI" => -2, "QA" => 0, 
        "SY" => -3, "LD" => -4, "GG" => 5,  "QB" => 1,  "SZ" => 0,  "LE" => -3, "XX" => -1, "BK" => 1,  "GH" => -2, "QC" => -5, "LF" => 2,  "XY" => -2, "BL" => -3, "VA" => 0,  "GI" => -3, "QD" => 2, 
        "LG" => -4, "XZ" => -1, "BM" => -2, "VB" => -2, "T*" => -8, "QE" => 2,  "LH" => -2, "BN" => 2,  "VC" => -2, "GK" => -2, "QF" => -5, "LI" => 2,  "VD" => -2, "GL" => -4, "QG" => -1, "**" => 1, 
        "BP" => -1, "VE" => -2, "Y*" => -8, "GM" => -3, "QH" => 3,  "LK" => -3, "BQ" => 1,  "VF" => -1, "GN" => 0,  "QI" => -2, "BR" => -1, "VG" => -1, "LL" => 6,  "BS" => 0,  "VH" => -2, "GP" => 0, 
        "QK" => 1,  "LM" => 4,  "BT" => 0,  "VI" => 4,  "GQ" => -1, "QL" => -2, "LN" => -3, "GR" => -3, "QM" => -1, "BV" => -2, "VK" => -2, "GS" => 1,  "QN" => 1,  "LP" => -3, "BW" => -5, "VL" => 2, 
        "LQ" => -2, "GT" => 0,  "BX" => -1, "VM" => 2,  "QP" => 0,  "LR" => -3, "BY" => -3, "VN" => -2, "LS" => -3, "GV" => -1, "BZ" => 2,  "QQ" => 4,  "LT" => -2, "GW" => -7, "VP" => -1, "QR" => 1, 
        "GX" => -1, "VQ" => -2, "QS" => -1, "LV" => 2,  "EA" => 0,  "GY" => -5, "VR" => -2, "QT" => -1, "LW" => -2, "EB" => 3,  "GZ" => 0,  "VS" => -1, "C*" => -8, "LX" => -1, "EC" => -5, "VT" => 0, 
        "QV" => -2, "LY" => -1, "ED" => 3,  "QW" => -5, "LZ" => -3, "QX" => -1, "EE" => 4,  "H*" => -8, "VV" => 4,  "QY" => -4, "EF" => -5, "VW" => -6, "QZ" => 3,  "M*" => -8, "EG" => 0,  "VX" => -1, 
        "EH" => 1,  "VY" => -2, "TA" => 1,  "EI" => -2, "VZ" => -2, "TB" => 0,  "R*" => -8, "*A" => -8, "TC" => -2, "EK" => 0,  "*B" => -8, "YA" => -3, "TD" => 0,  "EL" => -3, "*C" => -8, "YB" => -3, 
        "W*" => -8, "TE" => 0,  "EM" => -2, "*D" => -8, "YC" => 0,  "TF" => -3, "EN" => 1,  "*E" => -8, "YD" => -4, "TG" => 0,  "*F" => -8, "YE" => -4, "TH" => -1, "EP" => -1, "*G" => -8, "YF" => 7, 
        "TI" => 0,  "EQ" => 2,  "*H" => -8, "YG" => -5, "ER" => -1, "*I" => -8, "YH" => 0,  "TK" => 0,  "ES" => 0,  "YI" => -1, "TL" => -2, "ET" => 0,  "TM" => -1, "*K" => -8, "YK" => -4, "TN" => 0, 
        "EV" => -2, "*L" => -8, "YL" => -1, "EW" => -7, "*M" => -8, "YM" => -2, "TP" => 0,  "EX" => -1, "*N" => -8, "YN" => -2, "CA" => -2, "TQ" => -1, "EY" => -4, "CB" => -4, "TR" => -1, "EZ" => 3, 
        "*P" => -8, "YP" => -5, "TS" => 1,  "A*" => -8, "*Q" => -8, "YQ" => -4, "HA" => -1, "CC" => 12, "*R" => -8, "YR" => -4, "TT" => 3,  "HB" => 1,  "CD" => -5, "*S" => -8, "YS" => -3, "F*" => -8, 
        "HC" => -3, "CE" => -5, "*T" => -8, "YT" => -3, "TV" => 0,  "MA" => -1, "HD" => 1,  "CF" => -4, "TW" => -5, "MB" => -2, "HE" => 1,  "K*" => -8, "CG" => -3, "*V" => -8, "YV" => -2, "TX" => 0, 
        "MC" => -5, "HF" => -2, "CH" => -3, "*W" => -8, "YW" => 0,  "TY" => -3, "MD" => -3, "HG" => -2, "RA" => -2, "CI" => -2, "*X" => -8, "YX" => -2, "TZ" => -1, "ME" => -2, "P*" => -8, "RB" => -1, 
        "*Y" => -8, "MF" => 0,  "HH" => 6,  "RC" => -4, "CK" => -5, "*Z" => -8, "YY" => 10, "WA" => -6, "MG" => -3, "HI" => -2, "RD" => -1, "CL" => -6, "YZ" => -4, "WB" => -5, "MH" => -2, "RE" => -1, 
        "CM" => -5, "WC" => -8, "MI" => 2,  "HK" => 0,  "RF" => -4, "CN" => -4, "WD" => -7, "HL" => -2, "RG" => -3, "Z*" => -8, "WE" => -7, "MK" => 0,  "HM" => -2, "RH" => 2,  "CP" => -3, "WF" => 0, 
        "ML" => 4,  "HN" => 2,  "RI" => -2, "CQ" => -5, "WG" => -7, "CR" => -4, "WH" => -3, "HP" => 0,  "RK" => 3,  "CS" => 0,  "MM" => 6,  "WI" => -5, "HQ" => 3,  "RL" => -3, "CT" => -2, "MN" => -2, 
        "HR" => 2,  "RM" => 0,  "WK" => -3, "HS" => -1, "RN" => 0,  "CV" => -2, "MP" => -2, "WL" => -2, "HT" => -1, "CW" => -8, "MQ" => -1, "WM" => -4, "RP" => 0,  "CX" => -3, "MR" => 0,  "WN" => -4, 
        "HV" => -2, "RQ" => 1,  "CY" => 0,  "MS" => -2, "HW" => -3, "AA" => 2,  "CZ" => -5, "MT" => -1, "WP" => -6, "HX" => -1, "AB" => 0,  "RR" => 6,  "WQ" => -5, "HY" => 0,  "AC" => -2, "RS" => 0, 
        "MV" => 2,  "FA" => -3, "WR" => 2,  "HZ" => 2,  "AD" => 0,  "RT" => -1, "MW" => -4, "FB" => -4, "WS" => -2, "AE" => 0,  "D*" => -8, "MX" => -1, "FC" => -4, "WT" => -5, "AF" => -3, "RV" => -2, 
        "KA" => -1, "MY" => -2, "FD" => -6, "AG" => 1,  "RW" => 2,  "KB" => 1,  "MZ" => -2, "FE" => -5, "WV" => -6, "I*" => -8, "AH" => -1, "RX" => -1, "KC" => -5, "PA" => 1,  "AI" => -1, "RY" => -4, 
        "KD" => 0,  "FF" => 9,  "WW" => 17, "PB" => -1, "RZ" => 0,  "KE" => 0,  "N*" => -8, "FG" => -5, "WX" => -4, "PC" => -3, "AK" => -1, "KF" => -5, "FH" => -2, "WY" => 0,  "PD" => -1, "AL" => -2, 
        "KG" => -2, "FI" => 1,  "WZ" => -6, "PE" => -1, "AM" => -1, "S*" => -8, "KH" => 0,  "PF" => -5, "AN" => 0,  "KI" => -2, "FK" => -5, "ZA" => 0,  "PG" => 0,  "FL" => 2,  "X*" => -8, "ZB" => 2, 
        "PH" => 0,  "AP" => 1,  "FM" => 0,  "ZC" => -5, "AQ" => 0,  "KK" => 5,  "FN" => -3, "PI" => -2, "ZD" => 3,  "AR" => -2, "KL" => -3, "ZE" => 3,  "AS" => 1,  "KM" => 0,  "FP" => -5, "PK" => -1, 
        "ZF" => -5, "AT" => 1,  "KN" => 1,  "FQ" => -5, "PL" => -3, "ZG" => 0,  "FR" => -4, "PM" => -2, "ZH" => 2,  "AV" => 0,  "KP" => -1, "FS" => -3, "PN" => 0,  "ZI" => -2, "AW" => -6, "KQ" => 1, 
        "FT" => -3, "AX" => 0,  "KR" => 3,  "ZK" => 0,  "AY" => -3, "KS" => 0,  "FV" => -1, "PP" => 6,  "ZL" => -3, "AZ" => 0,  "KT" => 0,  "FW" => 0,  "PQ" => 0,  "ZM" => -2, "FX" => -2, "PR" => 0 
      }
  
  def initialize(gap_open = -11, gap_extension = -1)
    @gap_open, @gap_extension = gap_open, gap_extension
  end
  
  def align(sequence_1, sequence_2)
    # Sequence lengths
    length_1, length_2 = sequence_1.length, sequence_2.length
    # Dynamic programming matrix
  	matrix = (0..length_1).map { (0..length_2).map {} }
  	
  	# Initialization, (0, 0) = base case, (0, 1) and (1, 0) are open gap cases
  	matrix[0][0] = Cell.new(0, false)         # Base case
  	matrix[0][1] = Cell.new(@gap_open, true)  # Sequence 1 against a new gap
  	matrix[1][0] = Cell.new(@gap_open, true)  # Sequence 2 against a new gap
  	# Prep for gap costs, extends them across the length of both sequences
    (2..length_1).each { |i| matrix[i][0] = Cell.new(matrix[i-1][0].score + @gap_extension, true) } # Extend the gaps against sequence 1
    (2..length_2).each { |j| matrix[0][j] = Cell.new(matrix[0][j-1].score + @gap_extension, true) } # Extend the gaps against sequence 2
    
    # Fill out the matrix (for each position):
    (1..length_1).each do |i|
      (1..length_2).each do |j|
        # Choice 1: Align the two symbols
        choice_1 = matrix[i-1][j-1].score + PAIR_SCORES[(sequence_1[i-1].chr + sequence_2[j-1].chr).upcase]
        # Choice 2: Align sequence 1 against a gap and add the gap cost (conditional on if it's opening or an extension)
        choice_2 = matrix[i][j-1].score + (matrix[i][j-1].gap ? @gap_extension : @gap_open)
        # Choice 3: Align sequence 2 against a gap and add the gap cost (conditional on if it's opening or an extension)        
        choice_3 = matrix[i-1][j].score + (matrix[i-1][j].gap ? @gap_extension : @gap_open)
        # Select the best gapped match score
        best_gap_score = [choice_2, choice_3].max
        # The new cell is a match if the score is as good as the best gap score, otherwise the best gapped score
        matrix[i][j] = (choice_1 >= best_gap_score ? Cell.new(choice_1, false) : Cell.new(best_gap_score, true))
      end
    end

    # Initialize the new strings to generate
  	sequence_1_new, sequence_2_new = "", ""
  	i, j = length_1, length_2

    # While we haven't finished the backtrace...
  	while (i > 0 && j > 0)
  	  # Collect the current score, and all possible scores it could have been derived from
  		score      = matrix[i][j].score
  		score_diag = matrix[i-1][j-1].score
  		score_up   = matrix[i][j-1].score
  		score_left = matrix[i-1][j].score
  		
  		if (score == score_diag + PAIR_SCORES[(sequence_1[i-1].chr + sequence_2[j-1].chr).upcase])
  			# If the current score is the result of a symbol match, record the match and decrement the length of both strings
  			sequence_1_new = sequence_1[i-1].chr + sequence_1_new
  			sequence_2_new = sequence_2[j-1].chr + sequence_2_new
  			i -= 1
  			j -= 1
  		elsif (score == score_left + (matrix[i-1][j].gap ? @gap_extension : @gap_open))
  		  # If the current score is the result of matching sequence 1 against a gap, record the gap and decrement the length of sequence 1
  			sequence_1_new = sequence_1[i-1].chr + sequence_1_new
  			sequence_2_new = '-' + sequence_2_new
  			i -= 1
  		elsif (score == score_up + (matrix[i][j-1].gap ? @gap_extension : @gap_open))
  		  # If the current score is the result of matching sequence 2 against a gap, record the gap and decrement the length of sequence 2
  			sequence_1_new = '-' + sequence_1_new
  			sequence_2_new = sequence_2[j-1].chr + sequence_2_new
  			j -= 1
  		else
  		  raise "Can't follow the trace."
  		end
  	end
  	
  	# If there are remaining symbols in sequence 1, match them against gaps in sequence 2
  	while (i > 0)
  		sequence_1_new = sequence_1[i-1].chr + sequence_1_new
  		sequence_2_new = '-' + sequence_2_new
  		i -= 1		
  	end
  	# If there are remaining symbols in sequence 2, match them against gaps in sequence 1  	
  	while (j > 0)
  		sequence_1_new = '-' + sequence_1_new
  		sequence_2_new = sequence_2[j-1].chr + sequence_2_new
  		j -= 1
  	end
  	
  	# Returns an array of length three, with the first two entries holding a hash of the old and new sequences and the final entry as the score
  	[{:old_sequence => sequence_1, :new_sequence => sequence_1_new}, {:old_sequence => sequence_2, :new_sequence => sequence_2_new}, matrix[length_1][length_2].score] 
  end

  def self.matrix_to_hash(data)
    # Expects an array, where the first element is an array of labels (ie. ['A', 'T', 'C', 'G']) and subsequent entries are arrays of scores.
    
    #     A  G
    #  A 10 -2
    #  G -1  7    
    
    # ...would be formatted as:
    # [["A", "G"], [10, -2], [-1, 7]]
    
    # ...and would return the hash:
    # s = { 'AA' => 10,
    #       'AG' => -2,
    #       'GA' => -1,
    #       'GG' => 7 }
    
    labels, hash = data.shift, {}
    (0...labels.length).each do |i|
      (0...labels.length).each do |j|
        hash[labels[i] + labels[j]] = data[i][j]
      end
    end
    hash
  end
  
  def run_on_data
    # a = human, b = cow, c = worm, d = dog, e = zebrafish, f = paralog
    a = "MTGLALLYSGVFVAFWACALAVGVCYTIFDLGFRFDVAWFLTETSPFMWSNLGIGLAISLSVVGAAWGIYITGSSIIGGGVKAPRIKTKNLVSIIFCEAVAIYGIIMAIVISNMAEPFSATDPKAIGHRNYHAGYSMFGAGLTVGLSNLFCGVCVGIVGSGAALADAQNPSLFVKILIVEIFGSAIGLFGVIVAILQTSRVKMGD"  
    b = "MTGLVLLYSGVFVAFWACLLVVGICYTIFDLGFRFDVAWFLTETSPFMWSNLGIGLAISLSVVGAAWGIYITGSSIIGGGVKAPRIKTKNLVSIIFCEAVAIYGIIMAIVISNMAEPFSATDPKAIGHRNYHAGYSMFGAGLTVGLSNLFCGVCVGIVGSGAALADAQNPSLFVKILIVEIFGSAIGLFGVIVAILQTSRVKMGD"
    c = "MAAGAILKTTATLTVITTLIILGTGLFYMLSGQGHRFDIGWFLTSTSPHMWAGLGIGFSLSLSVLGAGWGIFTTGSSILGGGVKAPRIRTKNLVSIIFCEAVAIFGIIMAFVFVGKLAEFRREDLPDTEDGMAILARNLASGYMIFGGGLTVGLSNLVCGLAVGIVGSGAAIADAANPALFVKILIIEIFASAIGLFGMIIGIVQTNKASFGNK"
    d = "MTGLALLYSGVFVAFWACMVVVGICYTIFDLGFRFDVAWFLTETSPFMWSNLGIGLAISLSVVGAAWGIYITGSSIIGGGVKAPRIKTKNLVSIIFCEAVAIYGIIMAIVISNMAEPFSATDPQAIGHRNYHAGYSMFGAGLTVGLSNLFCGVCVGIVGSGAALADAQNPSLFVKILIVEIFGSAIGLFGVIVAILQTSRVKMGD"
    e = "MMNGHAILYTGVTLAFWSTMVIVGICYTIFDLGFRFDVAWFLTETSPFMWANLGIGLAISLSVVGAAWGIYITGSSIIGGGVKAPRIKTKNLVSIIFCEAVAIYGIIMAIVISNLAENFSGTTPETIGSKNYQAGYSMFGAGLTVGFSNLFCGICVGIVGSGAALADAQNANLFVRILIVEIFGSAIGLFGVIVAILQTSKVKMGN"
    f = "MSESKSGPEYASFFAVMGASAAMVFSALGAAYGTAKSGTGIAAMSVMRPEQIMKSIIPVVMAGIIAIYGLVVAVLIANSLNDDISLYKSFLQLGAGLSVGLSGLAAGFAIGIVGDAGVRGTAQQPRLFVGMILILIFAEVLGLYGLIVALILSTK"
    
    sequences = [a, b, c, d, e, f]
    (0...sequences.length).map { |i| (i+1...sequences.length).map { |j| (["Sequence ids: #{i}, #{j}"] << align(sequences[i], sequences[j])).flatten } }
  end
  
  class Cell
    attr_accessor :score, :gap
    
    def initialize(score, gap = false)
      @score, @gap = score, gap
    end
  end
end