if ARGV.first.nil?
  print "You must provide an argument, either: run, compile or clean.\n"
else
  case ARGV.first.strip.downcase
  when "clean"
    `rm *.class *.txt *.err`
  when "run"
    `javac *.java`
    `jar cmf ../Manifest.txt PhosphoScore.jar *.class`
    `java -jar PhosphoScore.jar`
  when "compile"
    `javac *.java`
    `jar cmf ../Manifest.txt PhosphoScore.jar *.class`
  else
    print "That command is not supported, run with no arguments to see usage.\n"
  end
end