"C:\Program Files\Java\jdk1.8.0_65\bin\java" -Didea.launcher.port=7539 "-Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA 14.1.4\bin" -Dfile.encoding=windows-1252 -classpath "C:\Program Files\Java\jdk1.8.0_65\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\rt.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_65\jre\lib\ext\zipfs.jar;C:\Users\j\Desktop\JPlaner\out\production\JPlaner;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 14.1.4\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain planner.graph.Main
Working Directory = C:\Users\j\Desktop\JPlaner/src/config/config.3.level.0.conf
[GoalState] :[Box-location(A,o1), Clean(o1), Clean(o2), Clean(o3), Clean(o4), Clean(o5), Clean(o6), Clean(o7), Clean(o8), Clean(o9), Empty(o2), Empty(o3), Empty(o4), Empty(o5), Empty(o6), Empty(o7), Empty(o8), Empty(o9), Robot-location(o9)]
[InitialState] :[Box-location(A,o5), Clean(o2), Clean(o3), Clean(o4), Clean(o6), Clean(o7), Dirty(o1), Clean(o5), Clean(o8), Clean(o9), Empty(o1), Empty(o2), Empty(o3), Empty(o4), Empty(o6), Empty(o7), Empty(o8), Empty(o9), Robot-location(o4)]
[Offices] :[o1, o2, o3, o4, o5, o6, o7, o8, o9]
[Boxes] :[A]
{o1=[o2, o4], o2=[o1, o3, o5], o3=[o2, o6], o4=[o1, o5, o7], o5=[o2, o4, o6, o8], o6=[o3, o5, o9], o7=[o4, o8], o8=[o5, o7, o9], o9=[o6, o8]}
There is a closer one: 2147483647 --> 4
0 Size [4]
1 Size [4]
There is a closer one: 4 --> 3
2 Size [3, 4]
3 Size [3, 4]
4 Size [3, 4]
5 Size [3, 4]
There is a closer one: 3 --> 2
There is a closer one: 2 --> 1
6 Size [1, 2, 3]
7 Size [1, 2, 3]
8 Size [1, 2, 3]
9 Size [1, 2, 3]
There is a closer one: 1 --> 0
Solution: 10 -> {1=Move(o4,o5), 2=Push(A,o5,o4), 3=Move(o4,o1), 4=Clean-Office(o1), 5=Move(o1,o4), 6=Push(A,o4,o1), 7=Move(o1,o2), 8=Move(o2,o5), 9=Move(o5,o6), 10=Move(o6,o9)}

------------------------------------------Results:
	IMPOSSIBLE: 0
	LOOPERPATH: 190
	FOUND_PATH: 0
	FORCE_QUIT: 0
	DUMMY_QUIT: 22
	SKIPP_QUIT: 0
------------------------------------------

Process finished with exit code 0