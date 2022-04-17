package com.alkaidmc.alkaid.bukkit.config;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonConfiguration {
    /**
     * 配置文件直转数据实体
     * 先加载插件配置文件夹中的文件
     * 如果没有则会先从 jar 中读取到插件文件夹再加载一次
     * 还是没有将导致异常
     * 加载成功后使用 Gson 转换成数据实体并返回
     * 需要重新加载则需要重新调用该方法
     *
     * @param plugin   插件实例
     * @param gson     json 解析器 可以使用 AlkaidGsonBuilder.create(); 获取包含 bukkit 序列化类型解析器
     * @param resource 在插件 jar 中的资源路径
     * @param path     在插件配置文件夹中的路径
     * @param type     数据实体类型
     * @param <T>      数据实体类型
     * @return 依赖注入后的数据实体
     */
    public <T> T load(JavaPlugin plugin, Gson gson, String resource, String path, Class<T> type) {
        // 检查资源文件
        File file = new File(plugin.getDataFolder(), path);
        // 否则尝试提取
        if (!file.exists()) {
            plugin.saveResource(resource, false);
            file = new File(plugin.getDataFolder(), path);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}