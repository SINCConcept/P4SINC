echo "note: ensure that policy.json api-map.json are ready in the current directory"
echo "usage: ./instrument.sh <app-jar to be instrumented>"
echo "Compiling the policies..."
java -jar tools/instrument.jar 
echo "Instrumenting the app..."
java -cp .:tools/aspectjtools.jar org.aspectj.tools.ajc.Main -cp .:tools/aspectjrt.jar -inpath $1 -outjar Instrumented-App.jar output.aj
echo "instrumented app: Instrumented-App.jar"
rm output.aj