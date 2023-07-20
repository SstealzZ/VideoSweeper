package fr.sstealzz.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Json {
    private File file;
    private List<Object> dataList;

    public Json(String filePath) {
        this.file = new File(filePath);
        this.dataList = new ArrayList<>();
    }

    public void append(Object data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Object> dataList = read(); // lire les données existantes
        dataList.add(data); // ajouter les nouvelles données à la fin de la liste

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(dataList, writer); // écrire la liste mise à jour dans le fichier JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        if (!file.exists()) {
            List<Object> dataList = new ArrayList<>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(dataList, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Object> read() {
        Gson gson = new Gson();
        List<Object> dataList = new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {
            dataList = gson.fromJson(reader, dataList.getClass());
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier n'existe pas.");
            e.printStackTrace();
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public void write(List<Data> dataList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(dataList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Data> getData() throws IOException {
        Gson gson = new Gson();
        Type dataListType = new TypeToken<List<Data>>() {}.getType();
        List<Data> dataList = gson.fromJson(new FileReader(file), dataListType);
        return dataList;
    }

    public Data getDataByName(String name) throws IOException {
        Gson gson = new Gson();
        Type dataListType = new TypeToken<List<Data>>() {}.getType();
        List<Data> dataList = gson.fromJson(new FileReader(file), dataListType);
        for (Data data : dataList) {
            if (data.getNameFile().equals(name)) {
                return data;
            }
        }
        return null;
    }

    public void modifyDataByName(String name) throws IOException {
        Gson gson = new Gson();
        Type dataListType = new TypeToken<List<Data>>() {}.getType();
        List<Data> dataList = gson.fromJson(new FileReader(file), dataListType);
        for (Data data : dataList) {
            if (data.getNameFile().equals(name)) {
                data.setPathFile(FileExplorer.findVideofileByName(name));
            }
        }
        write(dataList);
    }

    public List<String> getNames(String path) {
        List<String> names = new ArrayList<>();

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(path)) {
            Type type = new TypeToken<List<Data>>(){}.getType();
            List<Data> dataList = gson.fromJson(reader, type);
            for (Data data : dataList) {
                names.add(data.getNameFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return names;
    }

    public List<String> getPaths(String path) {
        List<String> paths = new ArrayList<>();

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(path)) {
            Type type = new TypeToken<List<Data>>(){}.getType();
            List<Data> dataList = gson.fromJson(reader, type);
            for (Data data : dataList) {
                paths.add(data.getPathFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paths;
    }

    public boolean isExistName(String name) {
        List<String> names = getNames("config.json");
        return names.contains(name);
    }

    public boolean isExistPath(String path) {
        List<String> paths = getPaths("config.json");
        return paths.contains(path);
    }

    public boolean isAlreadyExist(Data data) throws IOException{
        List<Data> dataList = getData();
        for (Data existingData : dataList) {
            if (existingData.getNameFile().equals(data.getNameFile())) {
                if (existingData.getPathFile().equals(data.getPathFile())) {
                    continue;
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }
}
