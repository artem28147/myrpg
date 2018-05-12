package com.mygdx.game;
import java.io.File;
import javax.swing.JFileChooser;

public class ModuleEngine {

    public static void main(String args[], MyMmorpg game) {

        /**
         * Получаем список доступных модулей.
         */
        JFileChooser fileopen = new JFileChooser(args[0]);
        int ret = fileopen.showDialog(null, "Открыть файл");
        String moduleName = null;
        String modulePath = null;
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            moduleName = file.getName().split(".class")[0];
            modulePath = (String)file.getPath();
        }

        /**
         * Создаем загрузчик модулей.
         */
        ModuleLoader loader = new ModuleLoader(modulePath, ClassLoader.getSystemClassLoader());

        /**
         * Загружаем и исполняем каждый модуль.
         */
        try {
            if (moduleName.equals("Module") == false) {
                System.out.print("Executing loading module: ");
                System.out.println(moduleName);

                Class clazz = loader.loadClass("com.mygdx.game."+moduleName);
                Module execute = (Module) clazz.newInstance();

                execute.load(game,execute);
                execute.unload();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}