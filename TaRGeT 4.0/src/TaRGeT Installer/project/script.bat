java -jar "%ECLIPSE_HOME%\startup.jar" -application org.eclipse.ant.core.antRunner -buildfile "%ECLIPSE_HOME%\plugins\org.eclipse.pde.build_3.2.0.v20060603\scripts\productBuild\productBuild.xml" -Dconfigs="win32, win32, x86" -DeclipseBaseURL="file:///%cd%/temp.zip" -DbaseLocation="%ECLIPSE_HOME%" -Dproduct="..\..\core\target.product" -DbuildDirectory="../release" -DarchivePrefix="TaRGeT" -DbuildId="TaRGeT" -DbuildLabel="TaRGeT" -DjavacSource="1.5" -DjavacTarget="1.5"
