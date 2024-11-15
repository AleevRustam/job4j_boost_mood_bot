package ru.job4j.servises;

import org.springframework.stereotype.Service;
import ru.job4j.content.Content;
import ru.job4j.content.ContentProvider;

import java.util.List;
import java.util.Random;

@Service
public class RecommendationEngine {
    /*private String beanName;

    @PostConstruct
    public void init() {
        System.out.println("Bean is going through @PostConstruct init.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Bean will be destroyed via @PreDestroy.");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean name from BeanNameAware: " + beanName);
    }*/

    private final List<ContentProvider> contents;
    private static final Random RND = new Random(System.currentTimeMillis());

    public RecommendationEngine(List<ContentProvider> contents) {
        this.contents = contents;
    }

    public Content recommendFor(Long chatId, Long moodId) {
        var index = RND.nextInt(0, contents.size());
        return contents.get(index).byMood(chatId, moodId);
    }
}
