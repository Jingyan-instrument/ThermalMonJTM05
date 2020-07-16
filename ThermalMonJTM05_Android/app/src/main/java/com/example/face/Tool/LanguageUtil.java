//Copyright © 2020 JingYan Instruments & Technology CO.,LTD.All rights reserved.

package com.example.face.Tool;

import android.graphics.Paint;

public class LanguageUtil {

    private static String[] envSet = {"تحديد درجة الحرارة المحيطة  ",
            "Temperature measurement environment setting",
            "Température ambiante",
            "Ambiente di misura della temperatura",
            "温度測定環境設定",
            "Condiciones climatológicas",
            "测温环境设置",
            "測溫環境設置"};
    public static String getMenuEnv(int mode){
        return envSet[mode];
    }

    private static String[] faceSet = {" التعرف على الوجه  ",
            "Face recognition settings",
            "Identification de visage",
            "Impostazioni di riconoscimento facciale",
            "顔認識設定",
            "Dispositivo de reconocimiento",
            "人脸识别设置",
            "人臉識別設置"};
    public static String getMenuFace(int mode){
        return faceSet[mode];
    }

    private static String[] lightSet = {"إعدادات الإضاءة ",
            "Light settings",
            "éclairage",
            "Impostazioni della luce",
            "ライトの設定",
            "Preferencias de iluminación",
            "灯光设置",
            "燈光設置"};
    public static String getMenuLight(int mode){
        return lightSet[mode];
    }



    private static String[] resetSet = {"استعادة إعدادات المصنع",
            "Restore factory settings",
            "Restauration des paramètres de sortie",
            "Ripristina le impostazioni della fabbrica",
            "出荷時設定を復元",
            "Restaurar configuración de fábrica",
            "恢复出厂设置",
            "恢復出廠設置"};
    public static String getMenuRest(int mode){
        return resetSet[mode];
    }




    private static String[] outSet = {"العودة إلى الصفحة الرئيسية",
            "Back to home",
            "Page d 'accueil",
            "Torna a casa",
            "ホームページに戻ります",
            "Volver a casa",
            "返回主页",
            "返回主頁"};
    public static String getMenuOut(int mode){
        return outSet[mode];
    }



    private static String[] languageSet = {"Language/ إعدادات اللغة",
            "Language settings",
            "Language/ Paramètres de langue",
            "Language/ Impostazioni linguaggio ",
            "Language/言語の設定",
            "Language / Language Design",
            "Language/语言设置",
            "Language/語言設置"};
    public static String getMenuLanguage(int mode){
        return languageSet[mode];
    }



    private static String[] tempPc = {"درجة الحرارة تعويض الانحراف  (℃):",
            "Temperature deviation compensation (℃):",
            "Compensation des écarts de température (c):",
            "Compensazione della deviazione della temperatura (℃):",
            "温度偏差補償(℃)：",
            "Compensación del sesgo de temperatura (°C):",
            "温度偏差补偿(℃):",
            "溫度偏差補償(℃):"};
    public static String getTempPc(int mode){
        return tempPc[mode];
    }



    private static String[] tempFactor = {"درجة الحرارة المحيطة عامل التعويض :",
            "Ambient temperature compensation factor:",
            "Facteurs de compensation de la température ambiante:",
            "Fattore di compensazione della temperatura ambiente:",
            "環境温度補償要因：",
            "Compensación de la temperatura ambiente",
            "环境温度补偿因素:",
            "環境溫度補償因素:"};
    public static String getTempFactor(int mode){
        return tempFactor[mode];
    }

    private static String[] tempLow = {"انخفاض درجة حرارة الجسم  (℃):",
            "Lower limit of human body temperature (℃):",
            "Seuil de basse température pour le corps humain (°C):",
            "Limite inferiore del corpo umano a bassa temperatura (℃):",
            "人体の低温下限値(℃)：",
            "Valores mínimos de criogenia humana (°C):",
            "人体低温下限值(℃):",
            "人體低溫下限值(℃):"};
    public static String getTempLow(int mode){
        return tempLow[mode];
    }



    private static String[] tempHig = {"ارتفاع درجة حرارة الجسم البشري قيمة التنبيه  (℃):",
            "Human body high temperature alarm value (℃):",
            "Taux d'alerte à haute température chez l'homme (°C):",
            "Valore di allarme ad alta temperatura per il corpo umano (℃):",
            "人体の高温警報値(℃)：",
            "Valores de alerta de calor humano (°C):",
            "人体高温报警值(℃):",
            "人體高溫報警值(℃):"};
    public static String getTempHig(int mode){
        return tempHig[mode];
    }



    private static String[] check = {"ألغى",
            "OK ",
            "Confirmer ",
            "OK ",
            "確定",
            "Determinación ",
            "确定",
            "確定"};
    public static String getCheck(int mode){
        return check[mode];
    }



    private static String[] cancel = {"حدد ",
            "cancel",
            "annuler",
            "annulla",
            "キャンセル",
            "cancelación",
            "取消",
            "取消"};
    public static String getCancel(int mode){
        return cancel[mode];
    }



    private static String[] resetCheck = {"لا",
            "Yes",
            "Oui ",
            "Sì",
            "はい、",
            "Sí. ",
            "是 ",
            "是 "};
    public static String getRestCheck(int mode){
        return resetCheck[mode];
    }



    private static String[] resetCancel = {"نعم ",
            "No",
            "Non",
            "no",
            "いいえ、",
            "No",
            "否",
            "否"};
    public static String getRestCancel(int mode){
        return resetCancel[mode];
    }



    private static String[] faceMin = {"الحد الأدنى من حجم التعرف على الوجه  (بيسل):",
            "Minimum face recognition size (pixels):",
            "Taille minimale de reconnaissance faciale (pixels):",
            "Dimensione minima del riconoscimento facciale (pixel):",
            "最小顔識別サイズ（ピクセル）：",
            "Tamaño mínimo de reconocimiento de la cara humana (píxeles):",
            "最小人脸识别尺寸(像素):",
            "最小人臉識別尺寸(像素):"};
    public static String getFaceMin(int mode){
        return faceMin[mode];
    }



    private static String[] faceMax = {"أقصى حجم التعرف على الوجه  (بيسل):",
            "Maximum face recognition size (pixels):",
            "Max Facial identification size (pixel):",
            "Dimensione di riconoscimento facciale massima (pixel):",
            "最大顔認識サイズ（ピクセル）：",
            "Tamaño máximo de reconocimiento de la cara humana (píxeles):",
            "最大人脸识别尺寸(像素):",
            "最大人臉識別尺寸(像素):"};
    public static String getFaceMax(int mode){
        return faceMax[mode];
    }



    private static String[] faceDistance = {"تعويض درجة حرارة جسم الإنسان بالجو (℃): ",
            "Human body distance temperature compensation (℃):",
            "Compensation de la température à distance du corps humain (°C):",
            "compensazione della temperatura della distanza corporea umana (℃):",
            "人体距離温度補償(℃)：",
            "Compensación de la temperatura de la distancia humana (°C):",
            "人体距离温度补偿(℃):",
            "人體距離溫度補償(℃):"};
    public static String getFaceDistance(int mode){
        return faceDistance[mode];
    }



    private static String[] faceProbability = {"التعرف على الوجه قيم (0-100%):",
            "Face recognition rate (0-100%):",
            "Taux de reconnaissance faciale (0 - 100%)",
            "Tasso di riconoscimento facciale (0-100%):",
            "顔認識率（0-100%）",
            "Tasa de reconocimiento facial (0 - 100%)",
            "人脸识别率(0-100%):",
            "人臉識別率(0-100%):"};
    public static String getFaceProbability(int mode){
        return faceProbability[mode];
    }



    private static String[] lightMode = {"الصمام الأبيض",
            "White LED: ",
            "Lumière blanche: ",
            "LED bianco:",
            "白色LED: ",
            "Luz blanca: ",
            "白光LED: ",
            "白光LED: "};
    public static String getLightMode(int mode){
        return lightMode[mode];
    }



    private static String[] lightClose = {"اتوماتیک",
            "Off ",
            "Désactiver ",
            "Off",
            "オフ",
            "Cerrar ",
            "关闭",
            "關閉"};
    public static String getLightClose(int mode){
        return lightClose[mode];
    }



    private static String[] lightOpen = {"فتح   ",
            "on ",
            "ouvrir ",
            "on ",
            "オン",
            "abrir ",
            "开启",
            "開啟"};
    public static String getLightOpen(int mode){
        return lightOpen[mode];
    }


    private static String[] lightCheck = {"نزل  ",
            "Auto",
            "automatiser",
            "Auto",
            "自動",
            "automatizar",
            "自动",
            "自動"};
    public static String getLightCheck(int mode){
        return lightCheck[mode];
    }



    private static String[] lightFz = {" التلقائي سطوع المحيطة",
            "Auto on ambient brightness: ",
            "Luminosité de l 'environnement à ouverture automatique: ",
            "Attiva automaticamente la luminosità ambientale:",
            "自動オープンの環境の明るさ: ",
            "Luminosidad ambiental activada automáticamente: ",
            "自动开启环境亮度: ",
            "自動開啓環境亮度: "};
    public static String getLightFz(int mode){
        return lightFz[mode];
    }



    private static String[] languageTitle ={"Language",
            "Language",
            "Language",
            "Language",
            "Language",
            "Language",
            "Language",
            "Language",
            "Language"};
    public static String getLanguageTitle(int mode){
        return languageTitle[mode];
    }




    private static String[] askReset = {"تحديد إعدادات استعادة المصنع ؟",
            "Are you sure to restore the factory settings?",
            "Vous êtes sûrs de rétablir les paramètres? ",
            "Sei sicuro di ripristinare le impostazioni della fabbrica?",
            "出荷時設定を再開しますか？",
            "¿Estás seguro de volver a la fábrica?",
            "确定恢复出厂设置?",
            "確定恢復出廠設置?"};
    public static String getAskRest(int mode){
        return askReset[mode];
    }



    private static String[] passMusic = {"PleasePass_AR.mp3",
            "PleasePass_EN.mp3",
            "PleasePass_FR.mp3",
            "PleasePass_IT.mp3",
            "PleasePass_JA.mp3",
            "PleasePass_ES.mp3",
            "PleasePass_CN.mp3",
            "PleasePass_HK.mp3",""};
    public static String getPassMusic(int mode){
        return passMusic[mode];
    }


    private static String[] dangerMusic = {"HighTemperature_AR.mp3",
            "HighTemperature_EN.mp3",
            "HighTemperature_FR.mp3",
            "HighTemperature_IT.mp3",
            "HighTemperature_JA.mp3",
            "HighTemperature_ES.mp3",
            "HighTemperature_CN.mp3",
            "HighTemperature_HK.mp3"};
    public static String getDangerMusic(int mode){
        return dangerMusic[mode];
    }


    private static String[] tempShowMode = {
            "عرض الصفحة الرئيسية  مئوية (℃) / فهرنهايت （℉）:",
            "The home page shows Celsius(℃) / Fahrenheit（℉）:",
            "La page d'accueil indique (°C) / Fahrenheit (°F):",
            "La home page mostra Celsius （℃）/ Fahrenheit（℉）:",
            "ホームページに摂氏(℃)/華氏(°F)：",
            "En la página de inicio se indica (°C) / Fahrenheit (°F):",
            "主页显示摄氏（℃）/ 华氏（℉）:",
            "主頁顯示攝氏(℃)/ 華氏（℉）:",
    };
    public static String getTempShowMode(int mode){
        return tempShowMode[mode];
    }

    private static String[] musicName = {
            "ضبط حجم",
            "Volume settings",
            "Ajustement de volume",
            "regolazione del volume",
            "音量調整",
            "Ajuste de volumen",
            "音量调整",
            "音量調整"
    };
    public static String getMusicName(int mode){
        return musicName[mode];
    }


    public static String[] setTitle = {
            "وضع مجموعة",
            "Mode settings",
            "Mode",
            "Impostazioni della modalità",
            "モード設定",
            "Configuración de modo",
            "模式设置",
            "模式設置"
    };

    public static String getSetTitle(int mode){
        return setTitle[mode];
    }


    public static String[] setModeTitle = {
            "طريقة التشغيل",
            "Operation mode",
            "Mode de fonctionnement",
            "Modalità di funzionamento",
            "操作モード",
            "Modo operativo",
            "操作模式",
            "操作模式"
    };



    //操作模式
    public static String getSetModeTitle(int mode){
        return setModeTitle[mode];
    }

    public static String[] quickTitle = {
            " طريقة الكشف الدقيق",
            "Quick filter mode",
            "Mode de sélection rapide",
            "Modalità filtro rapido",
            "クイックフィルタモード",
            "Modo de filtrado rápido",
            "快速筛选模式",
            "快速篩選模式"
    };

    //
    public static String getQuick(int mode){
        return quickTitle[mode];
    }


    public static String[] preciseTitle = {
            "طريقة الفحص السريع",
            "precise detection mode",
            "mode de détection de précision",
            "modalità di rilevamento precisa",
            "精確検出モード",
            "modo de ensayo preciso",
            "精确检测模式",
            "精確檢測模式"
    };

    public static String getPrecise(int mode){
        return preciseTitle[mode];
    }


    public static String[] faceModeTitle = {
            "طريقة اخفاء الوجه",
            "Face hiding mode",
            "Masquage de visage",
            "Modalità di nascondere facce",
            "顔隠しモード",
            "Modo oculto",
            "人脸隐藏模式",
            "人臉隱藏模式"
    };

    public static String getFaceModeTitle(int mode){
        return faceModeTitle[mode];
    }



}
