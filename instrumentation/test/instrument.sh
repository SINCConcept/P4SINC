#note: ensure that policy.json api-map.json are ready in the current directory
#usage: ./instrument.sh <app-jar to be instrumented> <api-map.json> <policy.json>
#output: Instrumented-App.jar
java -jar tools/Instrument.jar $2 $3 
java -cp .:tools/aspectjtools.jar org.aspectj.tools.ajc.Main -cp .:tools/aspectjrt.jar -inpath $1 -inpath Utility.jar -outjar Instrumented-App.jar output.aj
rm output.aj
