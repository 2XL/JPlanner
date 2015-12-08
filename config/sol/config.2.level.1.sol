"C:\Program Files\Java\jdk1.8.0_65\bin\java" -Didea.launcher.port=7538 "-Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA 14.1.4\bin" -Dfile.encoding=windows-1252 -classpath "C:\Program Files\Java\jdk1.8.0_65\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\rt.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\zipfs.jar;C:\Users\j\Desktop\JPlaner\out\production\JPlaner;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 14.1.4\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain planner.graph.Main
Working Directory = C:\Users\j\Desktop\JPlaner/src/config/config.2.level.1.conf
[GoalState] :[Box-location(A,o3), Box-location(B,o4), Clean(o1), Clean(o2), Clean(o3), Clean(o4), Empty(o1), Empty(o2), Robot-location(o1)]
[InitialState] :[Box-location(A,o1), Box-location(B,o2), Clean(o2), Clean(o3), Clean(o4), Dirty(o1), Empty(o3), Empty(o4), Robot-location(o4)]
[Offices] :[o1, o2, o3, o4]
[Boxes] :[A, B]
{o1=[o2, o3], o2=[o1, o4], o3=[o1, o4], o4=[o2, o3]}
There is a closer one: 2147483647 --> 6
0 Size [6]
1 Size [6]
There is a closer one: 6 --> 5
There is a closer one: 5 --> 4
2 Size [4, 5, 6]
3 Size [4]
There is a closer one: 4 --> 3
4 Size [3, 4]
There is a closer one: 3 --> 2
5 Size [2, 4]
6 Size [2, 3]
7 Size [2]
There is a closer one: 2 --> 1
8 Size [1]
There is a closer one: 1 --> 0
Solution: 9 -> {1=Move(o4,o3), 2=Move(o3,o1), 3=Push(A,o1,o3), 4=Move(o3,o4), 5=Push(B,o4,o2), 6=Push(B,o2,o4), 7=Move(o4,o3), 8=Move(o3,o1), 9=Clean-Office(o1)}

------------------------------------------Results:
	IMPOSSIBLE: 0
	LOOPERPATH: 23
	FOUND_PATH: 0
	FORCE_QUIT: 0
	DUMMY_QUIT: 13
	SKIPP_QUIT: 0
------------------------------------------

Process finished with exit code 0