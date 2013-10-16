:: Why an ugly one liner? So the cmd window stays open. Batch files are nonsense.
cmd /K "start gradle runServer && ping -n 6 127.0.0.1 >nul && gradle runArcade"