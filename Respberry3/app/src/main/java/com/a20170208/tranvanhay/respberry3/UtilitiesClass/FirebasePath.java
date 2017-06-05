package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

/**
 * Created by Van Hay on 03-Jun-17.
 */

public final class  FirebasePath {
        // Setter and getter path
    public static final String SENSOR_LIST_PATH = "SocketServer/NodeList/SensorNode/";
    public static final String SENSOR_CURRENT_VALUE_PATH = "SocketServer/NodeDetails/SensorNode/";
    public static final String SENSOR_VALUE_CONFIG_PATH = "SocketServer/Config/ValueConfig/";
    public static final String POWDEV_LIST_PATH = "SocketServer/NodeList/PowDevNode/";
    public static final String POWDEV_DETAILS_PATH = "SocketServer/NodeDetails/PowDevNode/";

        // Configuration path
    public static final String ZONE_SENSOR_NODE_CONFIG_PATH = "SocketServer/Config/ZoneConfig/SensorNode/";
    public static final String ZONE_POWDEV_NODE_CONFIG_PATH = "SocketServer/Config/ZoneConfig/PowDevNode/";
    public static final String MACADDR_AND_ID_MAPPING_PATH = "SocketServer/Config/MACAddrAndIDMapping";
    public static final String CONTROLLER_TYPE_CONFIG_PATH = "SocketServer/Config/ControllerConfig/Type";
    public static final String CONTROLLER_CONDITION_CONFIG_PATH = "SocketServer/Config/ControllerConfig/Condition";
}
