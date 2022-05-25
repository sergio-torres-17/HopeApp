package com.MultiDev.hopeapp.WebService;

public class ConstantesURL {
    //Direccion Backend Local
    public static final String URL_WS_LOCAL = "http://192.168.195.114/hopeappbackend/Request/";
    //Direccion Backend Pruebas
    public static final String UR_WS_PRUEBAS = "https://multicodemx.com/hopeapp/DEV/Request/";
    //Direccion Backend Productivo
    public static final String UR_WS_PRODUCTIVO = "https://multicodemx.com/hopeapp/PRD/Request/";

    public static final String URL_WS = UR_WS_PRUEBAS;
    /*************PETICIONES DOCTORES**************/
    public static final String URL_WS_DOCTORES = URL_WS;
    public static final String R_REGISTRO_DOCTORES = URL_WS_DOCTORES+"InsertarDoctor.php";
    public static final String R_LOGIN = URL_WS_DOCTORES+"LoginDoctor.php";
    public static final String R_LOGOUT_DOCTOR =URL_WS_DOCTORES+"LogOutDoctores.php";
    public static final String R_MODIFICAR_ESTATUS_CONEXION = URL_WS_DOCTORES+"ModificarEstatusConexion.php";
    public static final String R_MOSTRAR_ESTATUS_CONEXION =URL_WS_DOCTORES+"MostrarEstatusConexion.php";
    public static final String R_TRAER_INFO_POS_LOGIN = URL_WS_DOCTORES+"TraerInfoPosLogin.php";
    public static final String R_VALIDACION_EXISTENCIA_USUARIO = URL_WS_DOCTORES+"ValidacionExistenciaUsuario.php";
    public static final String R_VER_PACIENTES_SIN_TUTELA = URL_WS_DOCTORES+"VerPacientesSinTutela.php";
    public static final String R_VER_PACIENTE_DETALLADA = URL_WS_DOCTORES+"VerInfoPacienteDetallada.php";
    public static final String R_INSERTAR_TUTELA_PARA_PACIENTE = URL_WS_DOCTORES+"InsertarTutela.php";
    /*************************PETICIONES PACIENTES**************************/
    public static final String URL_WS_PACIENTES = URL_WS;
    public static final String R_TRAER_INFO_POS_LOGIN_PACIENTES = URL_WS_PACIENTES+"TraerInfoPosLoginPacientes.php";
    public static final String R_LOGIN_PACIENTES = URL_WS_PACIENTES+"LoginPacientes.php";
    public static final String R_INSERTAR_PACIENTE =URL_WS_PACIENTES+"InsertarPaciente.php";
    public static final String R_MOSTRAR_ETAPAS_CANCER = URL_WS_PACIENTES+"MostrarEtapasCancer.php";
    public static final String R_MOSTRAR_TIPOS_CANCER = URL_WS_PACIENTES+"MostrarTiposCancer.php";
    public static final String R_MOSTRAR_SINTOMAS = URL_WS_PACIENTES+"TraerSintomas.php";
    public static final String R_MOSTRAR_INTENSIDAD_SINTOMAS = URL_WS_PACIENTES+"TraerIntensidadSintomas.php";
    public static final String R_INSERTAR_SINTOMAS = URL_WS_PACIENTES+"InsertarSintoma.php";
    public static final String R_OBTENER_DOCTOR_A_CARGO = URL_WS_PACIENTES+"BuscarIdDoctorCargo.php";
    public static final String R_TRAER_INFO_PACIENTE_DETALLADA = URL_WS_PACIENTES+"TraInfoPacienteombre.php";
    /************************Peticiones GENERTICAS**************************/
    public static final String URL_WS_GENERICAS = URL_WS;
    public static final String R_VALIDAR_EXISTENCIA_USUARIO = URL_WS_GENERICAS+"ValidacionExistenciaUsuario.php";
    public static final String R_VER_TIPOS_USUARIOS = URL_WS_GENERICAS+"VerTipoUsuario.php";


}
