USUARIOS(CED_USU,CONT_USU,ROL_USU,NOM_USU,APE_USU,DIR_X,DIR_Y,DIR_USU_NORMAL) 
NEGOCIOS(RUC_NEG,CED_USU_PER,CAT_NEG,NOM_NEG,DIR_NEG_NORMAL,DIR_X_NEG,DIR_Y_NEG,CIU_NEG,PRO_NEGOCIO) 
CATEGORIAS(ID_CAT,NOM_CAT)
PRODUCTOS(ID_PRO,NOM_PRO,MAR_PRO,PRE_PRO,CAN_PRO,FEC_VEN_PRO,RUC_NEG_PER)
RESERVAS(NUM_RES,RUC_NEG,CED_USU,FEC_RES,VAL_TOT_RES)
DETALLE_RESERVA(NUM_DET,ID_PRO,CAN_PRO,SUB_TOTAL,NUM_RES_PER)

CREATE TABLE USUARIOS(
CED_USU VARCHAR(10) PRIMARY KEY,
CONT_USU VARCHAR(20) NOT NULL,
ROL_USU VARCHAR(10) NOT NULL,
NOM_USU VARCHAR(10) NOT NULL,
APE_USU VARCHAR(10) NOT NULL ,
DIR_X INT NOT NULL,
DIR_Y INT NOT NULL,
DIR_USU_NORMAL VARCHAR(10) NOT NULL
);

);

CREATE TABLE NEGOCIOS(
RUC_NEG VARCHAR(13) PRIMARY KEY,
CED_USU_PER VARCHAR(10) NOT NULL,
CAT_NEG VARCHAR(15) NOT NULL,
NOM_NEG VARCHAR(20) NOT NULL,
DIR_NEG_NOR VARCHAR(30) NOT NULL,
DIR_X INT NOT NULL,
DIR_Y INT NOT NULL,
CIU_NEG VARCHAR(15) NOT NULL,
PRO_NEG VARHCAR(15) NOT NULL
);

CREATE TABLE CATEGORIAS(
ID_CAT VARCHAR(10) PRIMARY KEY,
NOM_CAT VARCHAR(10) NOT NULL
);

RESERVAS(
NUM_RES INT PRIMARY KEY ,
RUC_NEG ,
CED_USU,
FEC_RES,
VAL_TOT_RES);