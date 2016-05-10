package com.maddog05.easysqlite.utils;

import android.database.Cursor;

/*
 * Created by maddog05 on 29/04/16.
 */
public class CursorUtils {
    public static Long checkLong(Cursor cursor, String name) {
        Long aux = Long.valueOf(0L);

        try {
            aux = Long.valueOf(cursor.isNull(cursor.getColumnIndex(name))?0L:Long.parseLong(cursor.getString(cursor.getColumnIndex(name))));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return aux;
    }

    public static Long checkLongWithNull(Cursor cursor, String name) {
        Long aux = null;

        try {
            aux = cursor.isNull(cursor.getColumnIndex(name))?null:Long.valueOf(Long.parseLong(cursor.getString(cursor.getColumnIndex(name))));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return aux;
    }

    public static String checkString(Cursor cursor, String name) {
        String aux = "";

        try {
            aux = cursor.isNull(cursor.getColumnIndex(name))?"":cursor.getString(cursor.getColumnIndex(name));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return aux;
    }

    public static String checkStringWithNull(Cursor cursor, String name) {
        String aux = null;

        try {
            aux = cursor.isNull(cursor.getColumnIndex(name))?null:cursor.getString(cursor.getColumnIndex(name));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return aux;
    }

    public static Integer checkInteger(Cursor cursor, String name) {
        int aux = 0;

        try {
            aux = cursor.isNull(cursor.getColumnIndex(name))?0:cursor.getInt(cursor.getColumnIndex(name));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return Integer.valueOf(aux);
    }

    public static Integer checkIntegerWithNull(Cursor cursor, String name) {
        Integer aux = null;

        try {
            aux = cursor.isNull(cursor.getColumnIndex(name))?null:Integer.valueOf(cursor.getInt(cursor.getColumnIndex(name)));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return aux;
    }

    public static Double checkDouble(Cursor cursor, String name) {
        Double aux = Double.valueOf(0.0D);

        try {
            aux = Double.valueOf(cursor.isNull(cursor.getColumnIndex(name))?0.0D:cursor.getDouble(cursor.getColumnIndex(name)));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return aux;
    }

    public static Double checkDoubleWithNull(Cursor cursor, String name) {
        Double aux = null;

        try {
            aux = cursor.isNull(cursor.getColumnIndex(name))?null:Double.valueOf(cursor.getDouble(cursor.getColumnIndex(name)));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return aux;
    }

    public static Boolean checkBoolean(Cursor cursor, String name) {
        Boolean aux = Boolean.valueOf(false);
        Integer aux2 = Integer.valueOf(0);

        try {
            aux2 = checkInteger(cursor, name);
            if(aux2.intValue() == 0) {
                aux = Boolean.valueOf(false);
            } else {
                aux = Boolean.valueOf(true);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return aux;
    }

    public static Boolean checkBooleanWithNull(Cursor cursor, String name) {
        Boolean aux = null;
        Integer aux2 = null;

        try {
            aux2 = checkInteger(cursor, name);
            if(aux2 != null) {
                if(aux2.intValue() == 0) {
                    aux = Boolean.valueOf(false);
                } else {
                    aux = Boolean.valueOf(true);
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return aux;
    }

    public static Object verifyValue(Cursor cursor, int columnIndex, Class mClass) {
        Object aux = null;
        if(mClass == String.class) {
            aux = cursor.getString(columnIndex);
        } else if(mClass == Integer.TYPE) {
            aux = Integer.valueOf(cursor.getInt(columnIndex));
        } else if(mClass == Long.TYPE) {
            aux = Long.valueOf(cursor.getLong(columnIndex));
        } else if(mClass == Boolean.TYPE) {
            aux = Boolean.valueOf(cursor.getInt(columnIndex) != 0);
        } else if(mClass == Double.TYPE) {
            aux = Double.valueOf(cursor.getDouble(columnIndex));
        }

        return aux;
    }
}
