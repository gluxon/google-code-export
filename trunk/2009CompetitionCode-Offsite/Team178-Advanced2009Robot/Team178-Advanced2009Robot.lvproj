<?xml version='1.0'?>
<Project Type="Project" LVVersion="8508002">
   <Item Name="My Computer" Type="My Computer">
      <Property Name="server.app.propertiesEnabled" Type="Bool">true</Property>
      <Property Name="server.control.propertiesEnabled" Type="Bool">true</Property>
      <Property Name="server.tcp.enabled" Type="Bool">false</Property>
      <Property Name="server.tcp.port" Type="Int">0</Property>
      <Property Name="server.tcp.serviceName" Type="Str">My Computer/VI Server</Property>
      <Property Name="server.tcp.serviceName.default" Type="Str">My Computer/VI Server</Property>
      <Property Name="server.vi.callsEnabled" Type="Bool">true</Property>
      <Property Name="server.vi.propertiesEnabled" Type="Bool">true</Property>
      <Property Name="specify.custom.address" Type="Bool">false</Property>
      <Item Name="TestServoControl.vi" Type="VI" URL="TestServoControl.vi"/>
      <Item Name="SampleVIInput.vi" Type="VI" URL="SampleVIInput.vi"/>
      <Item Name="MiddleMan.vi" Type="VI" URL="MiddleMan.vi"/>
      <Item Name="SampleOutput.vi" Type="VI" URL="SampleOutput.vi"/>
      <Item Name="test traction.vi" Type="VI" URL="test traction.vi"/>
      <Item Name="SimpleTractionControl.vi" Type="VI" URL="SimpleTractionControl.vi"/>
      <Item Name="TestButtons.vi" Type="VI" URL="TestButtons.vi"/>
      <Item Name="demo.vi" Type="VI" URL="demo.vi"/>
      <Item Name="Dependencies" Type="Dependencies">
         <Item Name="vi.lib" Type="Folder">
            <Item Name="Merge Errors.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/error.llb/Merge Errors.vi"/>
            <Item Name="Initialize Joystick.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/inputDevices.llb/Initialize Joystick.vi"/>
            <Item Name="errorList.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/inputDevices.llb/errorList.vi"/>
            <Item Name="Acquire Input Data.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/inputDevices.llb/Acquire Input Data.vi"/>
            <Item Name="joystickAcquire.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/inputDevices.llb/joystickAcquire.vi"/>
            <Item Name="keyboardAcquire.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/inputDevices.llb/keyboardAcquire.vi"/>
            <Item Name="mouseAcquire.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/inputDevices.llb/mouseAcquire.vi"/>
            <Item Name="subBuildXYGraph.vi" Type="VI" URL="/&lt;vilib&gt;/express/express controls/BuildXYGraphBlock.llb/subBuildXYGraph.vi"/>
            <Item Name="Dynamic To Waveform Array.vi" Type="VI" URL="/&lt;vilib&gt;/express/express shared/transition.llb/Dynamic To Waveform Array.vi"/>
            <Item Name="ex_CorrectErrorChain.vi" Type="VI" URL="/&lt;vilib&gt;/express/express shared/ex_CorrectErrorChain.vi"/>
            <Item Name="subSigGeneratorBlock.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalBlock.llb/subSigGeneratorBlock.vi"/>
            <Item Name="Nearest Frequency for Block.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalConfig.llb/Nearest Frequency for Block.vi"/>
            <Item Name="Nearest Freq in Int Cycles.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalConfig.llb/Nearest Freq in Int Cycles.vi"/>
            <Item Name="subInternalTiming.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalBlock.llb/subInternalTiming.vi"/>
            <Item Name="NI_MABase.lvlib" Type="Library" URL="/&lt;vilib&gt;/measure/NI_MABase.lvlib"/>
            <Item Name="NI_AALBase.lvlib" Type="Library" URL="/&lt;vilib&gt;/Analysis/NI_AALBase.lvlib"/>
            <Item Name="subShouldUseDefSigName.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalBlock.llb/subShouldUseDefSigName.vi"/>
            <Item Name="sub2ShouldUseDefSigName.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalBlock.llb/sub2ShouldUseDefSigName.vi"/>
            <Item Name="Clear Errors.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/error.llb/Clear Errors.vi"/>
            <Item Name="subGetSignalName.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalBlock.llb/subGetSignalName.vi"/>
            <Item Name="ex_GenAddAttribs.vi" Type="VI" URL="/&lt;vilib&gt;/express/express input/SimulateSignalBlock.llb/ex_GenAddAttribs.vi"/>
            <Item Name="ex_WaveformAttribsPlus.ctl" Type="VI" URL="/&lt;vilib&gt;/express/express shared/transition.llb/ex_WaveformAttribsPlus.ctl"/>
            <Item Name="Timestamp Subtract.vi" Type="VI" URL="/&lt;vilib&gt;/Waveform/TSOps.llb/Timestamp Subtract.vi"/>
            <Item Name="I128 Timestamp.ctl" Type="VI" URL="/&lt;vilib&gt;/Waveform/TSOps.llb/I128 Timestamp.ctl"/>
            <Item Name="DU64_U32SubtractWithBorrow.vi" Type="VI" URL="/&lt;vilib&gt;/Waveform/TSOps.llb/DU64_U32SubtractWithBorrow.vi"/>
            <Item Name="ex_SetExpAttribsAndT0.vi" Type="VI" URL="/&lt;vilib&gt;/express/express shared/transition.llb/ex_SetExpAttribsAndT0.vi"/>
            <Item Name="ex_WaveformAttribs.ctl" Type="VI" URL="/&lt;vilib&gt;/express/express shared/transition.llb/ex_WaveformAttribs.ctl"/>
            <Item Name="Timestamp Add.vi" Type="VI" URL="/&lt;vilib&gt;/Waveform/TSOps.llb/Timestamp Add.vi"/>
            <Item Name="DU64_U32AddWithOverflow.vi" Type="VI" URL="/&lt;vilib&gt;/Waveform/TSOps.llb/DU64_U32AddWithOverflow.vi"/>
            <Item Name="ex_SetAllExpressAttribs.vi" Type="VI" URL="/&lt;vilib&gt;/express/express shared/transition.llb/ex_SetAllExpressAttribs.vi"/>
            <Item Name="Waveform Array To Dynamic.vi" Type="VI" URL="/&lt;vilib&gt;/express/express shared/transition.llb/Waveform Array To Dynamic.vi"/>
         </Item>
         <Item Name="smooth.vi" Type="VI" URL="smooth.vi"/>
         <Item Name="JoystickButtonToggle.vi" Type="VI" URL="JoystickButtonToggle.vi"/>
         <Item Name="lvinput.dll" Type="Document" URL="../../../../../../Program Files/National Instruments/LabVIEW 8.5/resource/lvinput.dll"/>
         <Item Name="lvanlys.dll" Type="Document" URL="../../../../../../Program Files/National Instruments/LabVIEW 8.5/resource/lvanlys.dll"/>
      </Item>
      <Item Name="Build Specifications" Type="Build"/>
   </Item>
   <Item Name="RT CompactRIO Target" Type="RT CompactRIO">
      <Property Name="alias.name" Type="Str">RT CompactRIO Target</Property>
      <Property Name="alias.value" Type="Str">10.1.78.02</Property>
      <Property Name="CCSymbols" Type="Str">TARGET_TYPE,RT;OS,VxWorks;CPU,PowerPC;</Property>
      <Property Name="crio.family" Type="Str">901x</Property>
      <Property Name="host.ResponsivenessCheckEnabled" Type="Bool">true</Property>
      <Property Name="host.ResponsivenessCheckPingDelay" Type="UInt">5000</Property>
      <Property Name="host.ResponsivenessCheckPingTimeout" Type="UInt">1000</Property>
      <Property Name="host.TargetCPUID" Type="UInt">2</Property>
      <Property Name="host.TargetOSID" Type="UInt">14</Property>
      <Property Name="target.cleanupVisa" Type="Bool">false</Property>
      <Property Name="target.FPProtocolGlobals_ControlTimeLimit" Type="Int">300</Property>
      <Property Name="target.getDefault-&gt;WebServer.Port" Type="Int">80</Property>
      <Property Name="target.getDefault-&gt;WebServer.Timeout" Type="Int">60</Property>
      <Property Name="target.IsRemotePanelSupported" Type="Bool">true</Property>
      <Property Name="target.RTTarget.ApplicationPath" Type="Path">/c/ni-rt/startup/startup.rtexe</Property>
      <Property Name="target.RTTarget.EnableFileSharing" Type="Bool">true</Property>
      <Property Name="target.RTTarget.IPAccess" Type="Str">+*</Property>
      <Property Name="target.RTTarget.LaunchAppAtBoot" Type="Bool">true</Property>
      <Property Name="target.RTTarget.VIPath" Type="Path">/c/ni-rt/startup</Property>
      <Property Name="target.server.app.propertiesEnabled" Type="Bool">true</Property>
      <Property Name="target.server.control.propertiesEnabled" Type="Bool">true</Property>
      <Property Name="target.server.tcp.access" Type="Str">+*</Property>
      <Property Name="target.server.tcp.enabled" Type="Bool">true</Property>
      <Property Name="target.server.tcp.paranoid" Type="Bool">true</Property>
      <Property Name="target.server.tcp.port" Type="Int">3363</Property>
      <Property Name="target.server.tcp.serviceName" Type="Str"></Property>
      <Property Name="target.server.tcp.serviceName.default" Type="Str">Main Application Instance/VI Server</Property>
      <Property Name="target.server.vi.access" Type="Str">+*</Property>
      <Property Name="target.server.vi.callsEnabled" Type="Bool">true</Property>
      <Property Name="target.server.vi.propertiesEnabled" Type="Bool">true</Property>
      <Property Name="target.WebServer.Enabled" Type="Bool">false</Property>
      <Property Name="target.WebServer.LogEnabled" Type="Bool">false</Property>
      <Property Name="target.WebServer.LogPath" Type="Path">/c/ni-rt/system/www/www.log</Property>
      <Property Name="target.WebServer.Port" Type="Int">80</Property>
      <Property Name="target.WebServer.RootPath" Type="Path">/c/ni-rt/system/www</Property>
      <Property Name="target.WebServer.TcpAccess" Type="Str">c+*</Property>
      <Property Name="target.WebServer.Timeout" Type="Int">60</Property>
      <Property Name="target.WebServer.ViAccess" Type="Str">+*</Property>
      <Item Name="TypeDefs" Type="Folder">
         <Item Name="RobotData.ctl" Type="VI" URL="RobotData.ctl"/>
         <Item Name="VisionData.ctl" Type="VI" URL="VisionData.ctl"/>
         <Item Name="PeriodicTaskData.ctl" Type="VI" URL="PeriodicTaskData.ctl"/>
         <Item Name="Dashboard Datatype.ctl" Type="VI" URL="Dashboard Datatype.ctl"/>
      </Item>
      <Item Name="Team Code" Type="Folder">
         <Item Name="Robot Global Data.vi" Type="VI" URL="Robot Global Data.vi"/>
         <Item Name="Autonomous Iterative.vi" Type="VI" URL="Autonomous Iterative.vi"/>
         <Item Name="Autonomous Independent.vi" Type="VI" URL="Autonomous Independent.vi"/>
         <Item Name="Begin.vi" Type="VI" URL="Begin.vi"/>
         <Item Name="Vision Processing.vi" Type="VI" URL="Vision Processing.vi"/>
         <Item Name="Found (because i didn&apos;t want to imput all of vision data into autonomous).vi" Type="VI" URL="Found (because i didn&apos;t want to imput all of vision data into autonomous).vi"/>
         <Item Name="Disabled.vi" Type="VI" URL="Disabled.vi"/>
         <Item Name="Finish.vi" Type="VI" URL="Finish.vi"/>
         <Item Name="Two Color Servo Camera Examplev1.vi" Type="VI" URL="Two Color Servo Camera/Two Color Servo Camera Examplev1.vi"/>
         <Item Name="Periodic Tasks.vi" Type="VI" URL="Periodic Tasks.vi"/>
         <Item Name="Build DashBoard Data.vi" Type="VI" URL="Build DashBoard Data.vi"/>
         <Item Name="JoystickButtonToggle.vi" Type="VI" URL="JoystickButtonToggle.vi"/>
         <Item Name="smooth.vi" Type="VI" URL="smooth.vi"/>
      </Item>
      <Item Name="Robot Main.vi" Type="VI" URL="Robot Main.vi"/>
      <Item Name="Dependencies" Type="Dependencies">
         <Item Name="vi.lib" Type="Folder">
            <Item Name="NI_FPGA_Interface.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/NIFPGAInterface/NI_FPGA_Interface.lvlib"/>
            <Item Name="Joystick.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/Joystick/Joystick.lvlib"/>
            <Item Name="RobotDrive.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/RobotDrive/RobotDrive.lvlib"/>
            <Item Name="PWM.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/PWM/PWM.lvlib"/>
            <Item Name="DriverStation.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/DriverStation/DriverStation.lvlib"/>
            <Item Name="Image Type" Type="VI" URL="/&lt;vilib&gt;/vision/Image Controls.llb/Image Type"/>
            <Item Name="IMAQ Create" Type="VI" URL="/&lt;vilib&gt;/Vision/Basics.llb/IMAQ Create"/>
            <Item Name="IMAQ Image.ctl" Type="VI" URL="/&lt;vilib&gt;/vision/Image Controls.llb/IMAQ Image.ctl"/>
            <Item Name="Camera.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/Camera/Camera.lvlib"/>
            <Item Name="Watchdog.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/Watchdog/Watchdog.lvlib"/>
            <Item Name="nirio_resource_hc.ctl" Type="VI" URL="/&lt;vilib&gt;/userDefined/High Color/nirio_resource_hc.ctl"/>
            <Item Name="Error Cluster From Error Code.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/error.llb/Error Cluster From Error Code.vi"/>
            <Item Name="whitespace.ctl" Type="VI" URL="/&lt;vilib&gt;/Utility/error.llb/whitespace.ctl"/>
            <Item Name="Trim Whitespace.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/error.llb/Trim Whitespace.vi"/>
            <Item Name="StatusErrorCache.ctl" Type="VI" URL="/&lt;vilib&gt;/Robotics Library/WPI/DriverStation/StatusErrorCache.ctl"/>
            <Item Name="MiniMergeError.vi" Type="VI" URL="/&lt;vilib&gt;/Robotics Library/NIFPGAInterface/ErrorManagement/MiniMergeError.vi"/>
            <Item Name="Merge Errors.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/error.llb/Merge Errors.vi"/>
            <Item Name="MotorControl.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/PWM/MotorControl/MotorControl.lvlib"/>
            <Item Name="Clear Errors.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/error.llb/Clear Errors.vi"/>
            <Item Name="VariantType.lvlib" Type="Library" URL="/&lt;vilib&gt;/Utility/VariantDataType/VariantType.lvlib"/>
            <Item Name="DigitalModule.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/DigitalModule/DigitalModule.lvlib"/>
            <Item Name="Delay and Feed.vi" Type="VI" URL="/&lt;vilib&gt;/Robotics Library/WPI/Watchdog/Delay and Feed.vi"/>
            <Item Name="TCP Listen.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/tcp.llb/TCP Listen.vi"/>
            <Item Name="Internecine Avoider.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/tcp.llb/Internecine Avoider.vi"/>
            <Item Name="TCP Listen List Operations.ctl" Type="VI" URL="/&lt;vilib&gt;/Utility/tcp.llb/TCP Listen List Operations.ctl"/>
            <Item Name="TCP Listen Internal List.vi" Type="VI" URL="/&lt;vilib&gt;/Utility/tcp.llb/TCP Listen Internal List.vi"/>
            <Item Name="AnalogModule.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/AnalogModule/AnalogModule.lvlib"/>
            <Item Name="AnalogChannel.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/AnalogChannel/AnalogChannel.lvlib"/>
            <Item Name="Servo.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/PWM/Servo/Servo.lvlib"/>
            <Item Name="IMAQ Extract" Type="VI" URL="/&lt;vilib&gt;/vision/Image Manipulation.llb/IMAQ Extract"/>
            <Item Name="IMAQ Cast Image" Type="VI" URL="/&lt;vilib&gt;/Vision/Management.llb/IMAQ Cast Image"/>
            <Item Name="IMAQ ColorThreshold" Type="VI" URL="/&lt;vilib&gt;/Vision/Color Processing.llb/IMAQ ColorThreshold"/>
            <Item Name="Particle Parameters" Type="VI" URL="/&lt;vilib&gt;/vision/Image Controls.llb/Particle Parameters"/>
            <Item Name="IMAQ Particle Analysis" Type="VI" URL="/&lt;vilib&gt;/Vision/Analysis.llb/IMAQ Particle Analysis"/>
            <Item Name="IMAQ Or" Type="VI" URL="/&lt;vilib&gt;/Vision/Operator.llb/IMAQ Or"/>
            <Item Name="IMAQ ImageToImage 2" Type="VI" URL="/&lt;vilib&gt;/Vision/Management.llb/IMAQ ImageToImage 2"/>
            <Item Name="Encoder.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/Encoder/Encoder.lvlib"/>
            <Item Name="Counter.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/Counter/Counter.lvlib"/>
            <Item Name="EventCtrMode.ctl" Type="VI" URL="/&lt;vilib&gt;/Robotics Library/NIFPGAInterface/Counter/EventCtrMode.ctl"/>
            <Item Name="Relay.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/Relay/Relay.lvlib"/>
            <Item Name="DigitalInput.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/DigitalInput/DigitalInput.lvlib"/>
            <Item Name="DigitalOutput.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/DigitalOutput/DigitalOutput.lvlib"/>
            <Item Name="Ultrasonic.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/Ultrasonic/Ultrasonic.lvlib"/>
            <Item Name="DigitalInputFilter.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/DigitalInput/Filter/DigitalInputFilter.lvlib"/>
            <Item Name="AnalogTrigger.lvlib" Type="Library" URL="/&lt;vilib&gt;/Robotics Library/WPI/AnalogTrigger/AnalogTrigger.lvlib"/>
            <Item Name="Application Version.ctl" Type="VI" URL="/&lt;vilib&gt;/Utility/libraryn.llb/Application Version.ctl"/>
         </Item>
         <Item Name="Teleop.vi" Type="VI" URL="Teleop.vi"/>
         <Item Name="Control Statesv1.ctl" Type="VI" URL="Two Color Servo Camera/Control Statesv1.ctl"/>
         <Item Name="FPS Calculatorv1.vi" Type="VI" URL="Two Color Servo Camera/FPS Calculatorv1.vi"/>
         <Item Name="FindTwoColorv1.vi" Type="VI" URL="Two Color Servo Camera/FindTwoColorv1.vi"/>
         <Item Name="CreateImagesv1.vi" Type="VI" URL="Two Color Servo Camera/CreateImagesv1.vi"/>
         <Item Name="SizeOrderedMorphologyv1.vi" Type="VI" URL="Two Color Servo Camera/SizeOrderedMorphologyv1.vi"/>
         <Item Name="Team Designationv1.ctl" Type="VI" URL="Two Color Servo Camera/Team Designationv1.ctl"/>
         <Item Name="Green Rectsv1.vi" Type="VI" URL="Two Color Servo Camera/Green Rectsv1.vi"/>
         <Item Name="Test Second Colorv1.vi" Type="VI" URL="Two Color Servo Camera/Test Second Colorv1.vi"/>
         <Item Name="Compare Sizesv1.vi" Type="VI" URL="Two Color Servo Camera/Compare Sizesv1.vi"/>
         <Item Name="Merge Rects Verticallyv1.vi" Type="VI" URL="Two Color Servo Camera/Merge Rects Verticallyv1.vi"/>
         <Item Name="Split FF Infov1.vi" Type="VI" URL="Two Color Servo Camera/Split FF Infov1.vi"/>
         <Item Name="Single Target Infov1.ctl" Type="VI" URL="Two Color Servo Camera/Single Target Infov1.ctl"/>
         <Item Name="BiColor Maskv1.vi" Type="VI" URL="Two Color Servo Camera/BiColor Maskv1.vi"/>
         <Item Name="Servo Tracking State Machinev1.vi" Type="VI" URL="Two Color Servo Camera/Servo Tracking State Machinev1.vi"/>
         <Item Name="Center of Rectv1.vi" Type="VI" URL="Two Color Servo Camera/Center of Rectv1.vi"/>
         <Item Name="Deadbandv1.vi" Type="VI" URL="Two Color Servo Camera/Deadbandv1.vi"/>
         <Item Name="Servo Positions.vi" Type="VI" URL="Two Color Servo Camera/Servo Positions.vi"/>
         <Item Name="camera servo control.vi" Type="VI" URL="camera servo control.vi"/>
         <Item Name="NiRioSrv.dll" Type="Document" URL="NiRioSrv.dll"/>
         <Item Name="nivissvc.dll" Type="Document" URL="nivissvc.dll"/>
         <Item Name="nivision.dll" Type="Document" URL="nivision.dll"/>
         <Item Name="_nirio_device_handleType.ctl" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_handleType.ctl"/>
         <Item Name="_nirio_device_close.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_close.vi"/>
         <Item Name="_nirio_device_sessionStates.ctl" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_sessionStates.ctl"/>
         <Item Name="_nirio_device_attributesString.ctl" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_attributesString.ctl"/>
         <Item Name="_nirio_device_attrSetString.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_attrSetString.vi"/>
         <Item Name="_nirio_device_attributes.ctl" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_attributes.ctl"/>
         <Item Name="_nirio_device_attrSet32.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_attrSet32.vi"/>
         <Item Name="nirio_CleanUpAfterDownload.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_CleanUpAfterDownload.vi"/>
         <Item Name="_nirio_device_configSet.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_configSet.vi"/>
         <Item Name="Fifo_DMA_Config.ctl" Type="VI" URL="/&lt;vilib&gt;/rvi/FIFO/Fifo_Types/Fifo_DMA_Config.ctl"/>
         <Item Name="nirio_DMAReconfigureDriver.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_DMAReconfigureDriver.vi"/>
         <Item Name="nirio_EnableInterrupts.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_EnableInterrupts.vi"/>
         <Item Name="nirio_ConfigureRegisterAddresses.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_ConfigureRegisterAddresses.vi"/>
         <Item Name="_nirio_device_attrGet32.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_attrGet32.vi"/>
         <Item Name="nirio_DeviceFamilyEnum.ctl" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_Utility/nirio_DeviceFamilyEnum.ctl"/>
         <Item Name="nirio_MiteNTDeviceFamily.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_Utility/nirio_MiteNTDeviceFamily.vi"/>
         <Item Name="_nirio_device_writeBlock32.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_writeBlock32.vi"/>
         <Item Name="nirio_Write32.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_Write32.vi"/>
         <Item Name="_nirio_device_writeBlock8.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_writeBlock8.vi"/>
         <Item Name="_nirio_device_writeBlock16.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_writeBlock16.vi"/>
         <Item Name="_nirio_device_writeBlock.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_writeBlock.vi"/>
         <Item Name="nirio_Read8.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_Read8.vi"/>
         <Item Name="nirio_Write8.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_Write8.vi"/>
         <Item Name="nirio_Read32.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_Read32.vi"/>
         <Item Name="nirio_Download.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_Download.vi"/>
         <Item Name="nirio_DisableInterrupts.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_DisableInterrupts.vi"/>
         <Item Name="nirio_DMAStopAll.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_DMAStopAll.vi"/>
         <Item Name="nirio_MultilineStringToArray.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_Utility/nirio_MultilineStringToArray.vi"/>
         <Item Name="_nirio_device_attrGetString.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_attrGetString.vi"/>
         <Item Name="nirio_IsItOKToDownload.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_IsItOKToDownload.vi"/>
         <Item Name="_nirio_device_readBlock32.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_readBlock32.vi"/>
         <Item Name="niLvFpgaErrorClusterFromErrorCode.vi" Type="VI" URL="/&lt;vilib&gt;/rvi/errors/niLvFpgaErrorClusterFromErrorCode.vi"/>
         <Item Name="nirviErrorClusterFromErrorCode.vi" Type="VI" URL="/&lt;vilib&gt;/RVI Host/nirviSupport.llb/nirviErrorClusterFromErrorCode.vi"/>
         <Item Name="nirviFillInErrorInfo.vi" Type="VI" URL="/&lt;vilib&gt;/rvi/errors/nirviFillInErrorInfo.vi"/>
         <Item Name="nirviReportUnexpectedCaseInternalError (String).vi" Type="VI" URL="/&lt;vilib&gt;/rvi/errors/nirviReportUnexpectedCaseInternalError (String).vi"/>
         <Item Name="nirviReportUnexpectedCaseInternalError (U32).vi" Type="VI" URL="/&lt;vilib&gt;/rvi/errors/nirviReportUnexpectedCaseInternalError (U32).vi"/>
         <Item Name="nirviReportUnexpectedCaseInternalError (Bool).vi" Type="VI" URL="/&lt;vilib&gt;/rvi/errors/nirviReportUnexpectedCaseInternalError (Bool).vi"/>
         <Item Name="nirviReportUnexpectedCaseInternalError.vi" Type="VI" URL="/&lt;vilib&gt;/rvi/errors/nirviReportUnexpectedCaseInternalError.vi"/>
         <Item Name="nirio_PrepareForDownload.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_PrepareForDownload.vi"/>
         <Item Name="nirviRIOSetUpMiniMite.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirviRIOSetUpMiniMite.vi"/>
         <Item Name="nirio_AppVersionToI32.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_Utility/nirio_AppVersionToI32.vi"/>
         <Item Name="nirio_CheckDriverVersion.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_Utility/nirio_CheckDriverVersion.vi"/>
         <Item Name="_nirio_device_open.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_driverPrimitives.llb/_nirio_device_open.vi"/>
         <Item Name="nirio_Open.vi" Type="VI" URL="/&lt;vilib&gt;/LabVIEW Targets/FPGA/RIO/nirio_HostInterface/nirio_Open.vi"/>
         <Item Name="nirviWhatTheDeviceIsDoing.ctl" Type="VI" URL="/&lt;vilib&gt;/rvi/ClientSDK/nirviWhatTheDeviceIsDoing.ctl"/>
         <Item Name="niFPGADownloadSettings.ctl" Type="VI" URL="/&lt;vilib&gt;/rvi/interface/stock/niFPGADownloadSettings.ctl"/>
         <Item Name="nirviIntfOpen_cRIO-9074.vi" Type="VI" URL="/&lt;vilib&gt;/FPGAPlugInAG/cRIO-9074/nirviIntfOpen_cRIO-9074.vi"/>
      </Item>
      <Item Name="Build Specifications" Type="Build">
         <Item Name="FRC Robot Boot-up Deployment" Type="{69A947D5-514E-4E75-818E-69657C0547D8}">
            <Property Name="App_applicationGUID" Type="Str">{FA9EE9FC-BB86-4427-9BD6-2778CDC5E638}</Property>
            <Property Name="App_applicationName" Type="Str">startup.rtexe</Property>
            <Property Name="App_companyName" Type="Str">NI</Property>
            <Property Name="App_fileDescription" Type="Str">My Real-Time Application</Property>
            <Property Name="App_fileType" Type="Int">1</Property>
            <Property Name="App_fileVersion.major" Type="Int">1</Property>
            <Property Name="App_INI_aliasGUID" Type="Str">{81CAB607-961A-4950-820F-14767FC45DA2}</Property>
            <Property Name="App_INI_GUID" Type="Str">{7B37BDDE-3820-412F-930A-047C75125802}</Property>
            <Property Name="App_internalName" Type="Str">My Real-Time Application</Property>
            <Property Name="App_legalCopyright" Type="Str">Copyright © 2008 NI</Property>
            <Property Name="App_productName" Type="Str">My Real-Time Application</Property>
            <Property Name="Bld_buildSpecName" Type="Str">FRC Robot Boot-up Deployment</Property>
            <Property Name="Bld_excludePolymorphicVIs" Type="Bool">true</Property>
            <Property Name="Destination[0].destName" Type="Str">startup.rtexe</Property>
            <Property Name="Destination[0].path" Type="Path">/c/ni-rt/startup/internal.llb</Property>
            <Property Name="Destination[0].path.type" Type="Str">&lt;none&gt;</Property>
            <Property Name="Destination[0].type" Type="Str">App</Property>
            <Property Name="Destination[1].destName" Type="Str">Support Directory</Property>
            <Property Name="Destination[1].path" Type="Path">/c/ni-rt/startup/data</Property>
            <Property Name="Destination[1].path.type" Type="Str">&lt;none&gt;</Property>
            <Property Name="DestinationCount" Type="Int">2</Property>
            <Property Name="RTExe_localDestPath" Type="Path">/C/Users/Adam/Documents/FIRST/LabViewData/Builds</Property>
            <Property Name="RTExe_localDestPathType" Type="Str">&lt;none&gt;</Property>
            <Property Name="Source[0].itemID" Type="Str">{6ADBC885-30D8-426B-A87B-1374829E56BE}</Property>
            <Property Name="Source[0].type" Type="Str">Container</Property>
            <Property Name="Source[1].destinationIndex" Type="Int">0</Property>
            <Property Name="Source[1].itemID" Type="Ref">/RT CompactRIO Target/Robot Main.vi</Property>
            <Property Name="Source[1].sourceInclusion" Type="Str">TopLevel</Property>
            <Property Name="Source[1].type" Type="Str">VI</Property>
            <Property Name="SourceCount" Type="Int">2</Property>
         </Item>
      </Item>
   </Item>
</Project>
