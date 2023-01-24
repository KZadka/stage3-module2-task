package com.mjc.school.repository.data;

import com.mjc.school.repository.model.implementation.AuthorModel;
import com.mjc.school.repository.model.implementation.NewsModel;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSource {

    private static final int LINES_IN_FILE = 30;
    private static final int NUMBER_OF_NEWS_AND_AUTHORS = 20;

    private final List<AuthorModel> authors;
    private final List<NewsModel> news;
    private final Random random = new Random();

    private DataSource() {
        this.authors = getAuthorsInternally();
        this.news = getNewsInternally();
    }

    public List<AuthorModel> getAuthors() {
        return authors;
    }

    public List<NewsModel> getNews() {
        return news;
    }

    private List<AuthorModel> getAuthorsInternally() {
        List<AuthorModel> authorModels = new ArrayList<>();

        for (int i = 1; i <= NUMBER_OF_NEWS_AND_AUTHORS; ++i) {
            LocalDateTime date = dateCreator();
            authorModels.add(new AuthorModel((long) i, readFile("authors"), date, date));
        }
        return authorModels;
    }

    private List<NewsModel> getNewsInternally() {
        List<NewsModel> newsModels = new ArrayList<>();

        for (int i = 1; i <= NUMBER_OF_NEWS_AND_AUTHORS; ++i) {
            LocalDateTime date = dateCreator();
            newsModels.add(new NewsModel((long) i, readFile("news"),
                    readFile("content"), date, date,
                    authors.get(random.nextInt(authors.size())).getId()));
        }
        return newsModels;
    }

    private String readFile(String fileName) {

        int lineToGet = random.nextInt(LINES_IN_FILE);
        String contents = "";
        try {
            InputStream inputStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new IllegalArgumentException(fileName + " not found");
            }
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            for (int i = 0; i <= LINES_IN_FILE; i++) {
                contents = reader.readLine();
                if (i == lineToGet) {
                    return contents;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return contents;
    }

    private LocalDateTime dateCreator() {
        int minutesToSubtract = random.nextInt(555_000);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return now.minusMinutes(minutesToSubtract);
    }

}
