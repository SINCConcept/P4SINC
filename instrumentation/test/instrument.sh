#note: ensure that policy.json api-map.json are ready in the current directory
#usage: ./instrument.sh <app-jar to be instrumented>
#output: Instrumented-App.jar
java -jar tools/instrument.jar 
tools/ajc -cp .:tools/aspectjrt.jar -inpath $1 -outjar Instrumented-App.jar output.aj
rm output.aj