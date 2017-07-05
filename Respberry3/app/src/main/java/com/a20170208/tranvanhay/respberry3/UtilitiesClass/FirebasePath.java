package com.a20170208.tranvanhay.respberry3.UtilitiesClass;

/**
 * Created by Van Hay on 03-Jun-17.
 */

public final class  FirebasePath {
        // Setter and getter path
    public static final String SENSOR_LIST_PATH = "SocketServer/NodeList/SensorNode/";
    public static final String SENSOR_CURRENT_VALUE_PATH = "SocketServer/NodeDetails/SensorNode/";
    public static final String SENSOR_VALUE_DATABASE_PATH = "SocketServer/ValueDatabase/SensorNode/";
    public static final String SENSOR_VALUE_CONFIG_PATH = "SocketServer/Config/ValueConfig/";
    public static final String POWDEV_LIST_PATH = "SocketServer/NodeList/PowDevNode/";
    public static final String POWDEV_DETAILS_PATH = "SocketServer/NodeDetails/PowDevNode/";
    public static final String ALERT_DATABASE_PATH = "SocketServer/AlertDatabase";

        // Configuration path
    public static final String ZONE_SENSOR_NODE_CONFIG_PATH = "SocketServer/Config/ZoneConfig/SensorNode/";
    public static final String ZONE_POWDEV_NODE_CONFIG_PATH = "SocketServer/Config/ZoneConfig/PowDevNode/";
    public static final String MACADDR_AND_ID_MAPPING_PATH = "SocketServer/Config/MACAddrAndIDMapping";
    public static final String TIME_SAVE_IN_DATABASE_PATH = "SocketServer/Config/TimeSaveInDatabase";
    public static final String CONTROLLER_ALERT_TYPE_CONFIG_PATH = "SocketServer/Config/ControllerConfig/AlertType";
    public static final String CONTROLLER_AUTO_OPERATION_PATH = "SocketServer/Config/ControllerConfig/AutoOperation";
    public static final String CONTROLLER_GSM_NODE_PATH = "SocketServer/Config/ControllerConfig/GSMNode";

        // Storage path
    public static final String IMAGE_LINK_PATH = "ImageStorage/ImageLink";
    public static final String IMAGE_TIME_CAPUTRE_PATH = "ImageStorage/TimeCapture";
    public static final String CAMERA_STATUS_PATH = "ImageStorage/CameraStatus";
    public static final String CAMERA_CONTROL = "ImageStorage/CameraControl";
}
