package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.NewsAddBindingModel;
import com.example.star_wars_project.model.entity.*;
import com.example.star_wars_project.model.view.AllNewsViewModel;
import com.example.star_wars_project.repository.*;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final ModelMapper modelMapper;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;

    public NewsServiceImpl(ModelMapper modelMapper, NewsRepository newsRepository, UserRepository userRepository, PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<AllNewsViewModel> latestStarWarsNews() {
        return newsRepository
                .findLatestThreeNews()
                .stream()
                .map(this::mapsNewsToNewsView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllNewsViewModel> findAllNewsWithValueNullOrFalse() {
        return newsRepository
                .findNewsThatAreNotApproved()
                .stream()
                .map(this::mapsNewsToNewsView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllNewsViewModel> findAllNews() {
        return newsRepository
                .findAllNewsOrderedByNewestToOldest()
                .stream()
                .map(news -> {
                    AllNewsViewModel currentNews = mapsNewsToNewsView(news);
                    currentNews.setAuthorName(news.getAuthor().getUsername());
                    return currentNews;
                }).collect(Collectors.toList());
    }

    private AllNewsViewModel mapsNewsToNewsView(News news) {
        AllNewsViewModel currentNews = modelMapper.map(news, AllNewsViewModel.class);
        Picture currentPicture = pictureRepository.findPictureByNews_Id(news.getId());
        currentNews.setPicture(currentPicture);
        return currentNews;
    }

    @Override
    public void addNews(NewsAddBindingModel newsAddBindingModel, String currentUserUsername) throws IOException {
        News news = modelMapper.map(newsAddBindingModel, News.class);
        news.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        newsRepository.save(news);

        MultipartFile pictureMultipartFile = newsAddBindingModel.getPicture();
        String pictureMultipartFileTitle = newsAddBindingModel.getPictureTitle();
        final CloudinaryImage uploaded = cloudinaryService.upload(pictureMultipartFile);
        Picture picture = new Picture();

        picture.setPictureUrl(uploaded.getUrl());
        picture.setPublicId(uploaded.getPublicId());

        picture.setTitle(pictureMultipartFileTitle);
        picture.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));

        picture.setNews(newsRepository.findNewsByTitle(newsAddBindingModel.getTitle()));
        pictureRepository.save(picture);
    }

    @Override
    public News findNews(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public void approveNewsWithId(Long id) {
        News news = newsRepository.findNewsById(id);
        news.setApproved(true);
        newsRepository.save(news);
    }

    @Override
    public void deleteNewsWithId(Long id) {
        List<Picture> allByNewsId = pictureRepository.findAllByNews_Id(id);
        pictureRepository.deleteAll(allByNewsId);
        newsRepository.deleteById(id);
    }

    @Override
    public void deleteOlderNews() {
        LocalDateTime dateAndTimeNow = LocalDateTime.now();
        LocalDateTime localDateTime = dateAndTimeNow.minusMonths(3);
        List<News> allByPostDateBefore = newsRepository.findAllByPostDateBefore(localDateTime);

        if (allByPostDateBefore.isEmpty()) {
            return;
        }

        for (News news : allByPostDateBefore) {
            Picture pictureByNewsId = pictureRepository.findPictureByNews_Id(news.getId());
            pictureRepository.delete(pictureByNewsId);
        }

        newsRepository.deleteAll(allByPostDateBefore);
    }

    @Override
    public void initNews() {
        if (newsRepository.count() > 0) {
            return;
        }

        News news1 = new News();
        news1.setApproved(null);
        news1.setDescription("Note: Following publication of this article, the release date of Star Wars Jedi: Survivor was moved to April 28, 2023. This has been reflected below.\n" +
                "\n" +
                "When Star Wars Jedi: Fallen Order arrived in November 2019, Cameron Monaghan had an easy holiday gift for family and friends. Monaghan had played the game’s protagonist, Cal Kestis, a Padawan survivor of Order 66, so it made for something of a perfect present. But while seeing himself in a game didn’t faze the actor, the same can’t be said for his loved ones.\n" +
                "\n" +
                "“I’d go over [to] their house and they’d be playing it, and it would be really weird,” he tells StarWars.com, laughing. As a joke, Monaghan would sit near friends, echoing the game’s dialogue or Cal’s grunts and screams as they happened on-screen. “I remember my friends being really tripped out. ‘What’s happening?!’”\n" +
                "\n" +
                "In Jedi: Fallen Order, set during the age of the Empire and Jedi-hunting Inquisitors, Cal was just looking to lay low and exist until fate intervened. Though his training was incomplete, he was thrust back into the fight and the ways of the Jedi; the rest is gaming history. Jedi: Fallen Order went on to become one of the most beloved Star Wars games, and Cal a modern fan favorite -- so much so that you can currently purchase Cal’s Legacy Lightsaber Hilt from Star Wars: Galaxy’s Edge in the Disneyland Park in California and the Walt Disney World Resort in Florida, the result of a fan vote. A sequel, Star Wars Jedi: Survivor, was announced in January, and last night at The Game Awards, Respawn Entertainment, Electronic Arts, and Lucasfilm Games confirmed the release date — April 28, 2023, on PlayStation 5, Xbox Series X|S, and Windows PC — and dropped the first full trailer. It's a stirring trailer, showcasing new enemies, allies, and abilities, while ending with some powerful words from Cal: “As long as we fight, hope survives.” For Monaghan, the trailer represents what the game experience will be.\n" +
                "\n" +
                "“I think it’s a great introduction into the scope and the size of this new game. This game is really ambitious in how much it is furthering and progressing not only the gameplay elements from Jedi: Fallen Order, but also furthering the story and the characters as well,” Monaghan says. “We’re starting to see hints as to where that’s going to branch off and what options you’re going to be given as a player.”\n" +
                "\n" +
                "He's quick to note, however, that the trailer is really just a small preview. “As much as the trailer does reveal some really cool and exciting things, it doesn’t give away the whole gist of what we’re going for with this one,” Monaghan says. “I think people are going to be really pleasantly surprised by just how this game progresses and where it goes as you get deeper into it.” There’s a five-year time jump between Jedi: Fallen Order and Jedi: Survivor, and the Empire has been expanding. As the Imperials tighten their grip, rebels and outlaws feel the squeeze. “We have a really dire situation that we find Cal in at the beginning of the game, where he is searching for ways to rebel that are becoming increasingly difficult and perhaps even hopeless. But we have a character that was given meaning and a goal by this resistance, and he was given this hope, and that hope is very hard to extinguish,” Monaghan says. “But what happens when you have hope in a situation that becomes increasingly hopeless? Different people have different ideas about what is the best way to resist.”\n" +
                "\n" +
                "Monaghan is perhaps alluding to conflicts like one glimpsed in the trailer, in which Cere Junda (played by Debra Wilson), a former Jedi who trained Cal in Jedi: Fallen Order, seems to take issue with Cal’s methods. “Cere and Cal have this interesting dynamic, where she’s not his formal master, but she is his mentor,” he says. “And, frankly, they have different ideas of how to deal with imperialism and the Empire. In the first game, it was about finding family and what it means to create a family. With this story, a lot of it is to do with 'What happens when there’s stress on a family?'” With elements like this, and others that Monaghan can’t talk about yet, he sees Cal’s journey in the game as especially rich.\n" +
                "\n" +
                "“What we end up finding in the story is a really complicated and exciting development to how we want to approach the character, and I think it is not only exciting, but it’s also emotional and beautiful and treats its audience with a level of respect that I think a lot of people are going to really appreciate.”A gamer himself, Monaghan understands the legacy of Star Wars games, which stretches back to the early ‘80s. He sees Jedi: Survivor as playing a potentially significant role in the current generation of consoles. “It’s an honor to be a part of a title releasing now in this generation of consoles with the Jedi series. I think that people have been looking for the titles that really excite them to make the leap on to the next generation,” he says. “Speaking personally, having seen how this game looks and how it runs on the next generation [of consoles], I think it’s going to have people very excited.”\n" +
                "\n" +
                "Still, the actor seems most excited about the story. While he won’t go into specifics, it sounds like Jedi: Survivor will make an impact. “This is going to be an emotional rollercoaster for people,” he says. “I think that you’d have to be made of stone for it to not hit you in the heart.”");
        news1.setTitle("Star Wars Jedi: Survivor: Cameron Monaghan on Cal Kestis’ Next Adventure");
        news1.setPostDate(LocalDateTime.of(2023, 12, 7, 12, 30));
        news1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));

        Picture picture1 = new Picture();
        picture1.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680196719/Star_Wars_Jedi_Survivor_r3labi.webp");
        picture1.setPublicId("Star_Wars_Jedi_Survivor_r3labi");
        picture1.setTitle("Star Wars Jedi: Survivor: Cameron Monaghan on Cal Kestis’ Next Adventure");
        picture1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture1.setNews(news1);

        newsRepository.save(news1);
        pictureRepository.save(picture1);


        News news2 = new News();
        news2.setApproved(null);
        news2.setDescription("The cavalry -- Omega, Hunter, Wrecker, Echo, and Tech -- has arrived!\n" +
                "\n" +
                "Today, Lucasfilm Animation and Disney+ released the trailer and key art for Season 2 of the acclaimed animated series Star Wars: The Bad Batch, with the two-episode season premiere on January 4, 2023, following the continuing adventures of the elite squad first introduced in Star Wars: The Clone Wars." +
                "When Season 2 debuts, viewers will find that months have passed since the events on Kamino that brought Season 1 to its thrilling conclusion, as the Bad Batch -- also known as Clone Force 99 -- continues to navigate the Empire after the fall of the Republic. The Batch will cross paths with friends and foes, both new and familiar, as they take on a variety of mercenary missions that will take them to unexpected and dangerous new places.\n" +
                "\n" +
                "Star Wars: The Bad Batch Season 2 stars Emmy Award nominee Dee Bradley Baker (American Dad!) as the voice of the Bad Batch and Emmy Award nominee Michelle Ang (Fear the Walking Dead: Flight 462) as the voice of Omega. Emmy Award winner Rhea Perlman (The Mindy Project, Cheers) returns to guest star as Cid, Noshir Dalal (It's Pony, The Owl House) returns to guest star as Vice Admiral Rampart, and Emmy Award winner Wanda Sykes (The Upshaws, Black-ish) makes her guest starring debut in the series as Phee Genoa." +
                "Star Wars: The Bad Batch is executive produced by Dave Filoni (The Mandalorian, Star Wars: The Clone Wars), Athena Portillo (Star Wars: The Clone Wars, Star Wars Rebels), Brad Rau (Star Wars Rebels, Star Wars Resistance), Jennifer Corbett (Star Wars Resistance, NCIS) and Carrie Beck (The Mandalorian, Star Wars Rebels) with Josh Rimes (Star Wars Resistance, Star Wars: Visions) and Alex Spotswood (Star Wars: The Clone Wars, Star Wars Rebels) as producers. Rau is also serving as supervising director with Corbett as head writer and Matt Michnovetz as story editor.\n" +
                "\n" +
                "Star Wars: The Bad Batch streams exclusively on Disney+ beginning January 4, 2023, with a two-episode premiere.\n" +
                "\n" +
                "Here's the full list of air dates and episode titles:\n" +
                "\n" +
                "January 4, 2023 — Episode 201 “Spoils of War” & Episode 202 “Ruins of War”\n" +
                "\n" +
                "January 11, 2023 — Episode 203 “The Solitary Clone”\n" +
                "\n" +
                "January 18, 2023 — Episode 204 “Faster”\n" +
                "\n" +
                "January 25, 2023 — Episode 205 “Entombed”\n" +
                "\n" +
                "February 1, 2023 — Episode 206 “Tribe”\n" +
                "\n" +
                "February 8, 2023 — Episode 207 “The Clone Conspiracy” & Episode 208 “Truth and Consequences”\n" +
                "\n" +
                "February 15, 2023 — Episode 209 “The Crossing”\n" +
                "\n" +
                "February 22, 2023 — Episode 210 “Retrieval”\n" +
                "\n" +
                "March 1, 2023 — Episode 211 “Metamorphosis”\n" +
                "\n" +
                "March 8, 2023 — Episode 212 “The Outpost”\n" +
                "\n" +
                "March 15, 2023 — Episode 213 “Pabu”\n" +
                "\n" +
                "March 22, 2023 — Episode 214 “Tipping Point”\n" +
                "\n" +
                "March 29, 2023 — Episode 215 “The Summit” & Episode 216 “Plan 99”\n" +
                "\n" +
                "Mark your calendars, gather your crew, and brush up on Season 1 now, because Clone Force 99 is on its way.");
        news2.setTitle("Clone Force 99 Is Back in New Star Wars: The Bad Batch Season 2 Trailer");
        news2.setPostDate(LocalDateTime.of(2023, 12, 7, 10, 30));
        news2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));

        Picture picture2 = new Picture();
        picture2.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680196958/Clone_Force_99_Is_Back_in_New_Star_Wars_The_Bad_Batch_Season_2_Trailer_i79bha.webp");
        picture2.setPublicId("Clone_Force_99_Is_Back_in_New_Star_Wars_The_Bad_Batch_Season_2_Trailer_i79bha");
        picture2.setTitle("Clone Force 99 Is Back in New Star Wars: The Bad Batch Season 2 Trailer");
        picture2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture2.setNews(news2);

        newsRepository.save(news2);
        pictureRepository.save(picture2);


        News news3 = new News();
        news3.setApproved(null);
        news3.setDescription("Katee Sackhoff has played Bo-Katan Kryze for over a decade, starting as the voice of the redheaded Mandalorian on Star Wars: The Clones Wars, but she’s still getting used to inhabiting the role in live action on The Mandalorian.\n" +
                "\n" +
                "As Season 3 of the hit Disney+ series continues with new episodes each Wednesday, Bo-Katan has emerged as a formidable co-star to Pedro Pascal’s Din Djarin, the eponymous Mandalorian, and the fan-favorite Grogu. In the season premiere, we found the one-time heir to the throne of Mandalore now sitting idly by on her throne on Kalevala. Well, maybe not entirely idle. “Like a baller?” Sackhoff offers helpfully. The unique posture was the result of Sackhoff and show creator Jon Favreau working to define the character’s current state of being. “That was a really big thing for Jon and I,” Sackhoff tells StarWars.com. “He kept having me sit, and then walk back, and sit, and walk back. He wanted it to seem…slightly disrespectful,” she adds, pausing to laugh mid-thought. “It's not the way that you would normally see royalty sit on a throne. I think that may be a sort of metaphor for part of her issues.”\n" +
                "\n" +
                "Sackhoff speaks about Bo-Katan as one might an old friend (she’s previously told StarWars.com about dreaming as her animated counterpart). Still, it’s taken some adjusting to accept her own face staring back at her in the full armor of Lady Kryze, Sackhoff says. “You know, it's a completely different medium and I don't think I realized how different it was, to be honest, until I actually stepped on stage. I thought that I had done all of the work and realized that I didn't actually know how Bo-Katan moved and talked and held herself. All of a sudden, I felt completely unprepared even though I knew so much about her and her backstory.”" +
                "In Season 2 of The Mandalorian, Bo-Katan’s first appearance in live action felt to Sackhoff like creators were paying homage to the animated Kryze. “I don't think anybody really anticipated that she would be back to this extent. And if they did, they didn't tell me!” she continues. “For this season, I wanted to lean in and own pieces of her more. We worked on the hair and her freckles might be a little bit more pronounced. I wanted her to look slightly different, not so much just cut out of animation, but that she looks like she belongs in this world.”" +
                "Having stunt doubles in the series is a departure from when Sackhoff first entered showbusiness, ultimately winning her physically-demanding breakout role as Kara “Starbuck” Thrace on Battlestar Galactica. “When I started in this industry, it was really hard to find doubles to match me,” Sackhoff says. “You know, I was always just slightly bigger than most of the women around that were actors at the time and the stunt doubles, they just couldn't find anyone. I was doubled by a man at one point.” The disparity pushed Sackhoff to perform more of her own stunt work. “It was literally me doing my own stunts because the match was not perfect, and I wanted it to look believable.”" +
                "As the industry has evolved and Sackhoff has gotten older, things have changed. “I've realized that, number one, I don't bounce back as well as I used to,” Sackhoff says. “But also, I think that if I take my ego out of it and, and acknowledge that we are trying to create the best character and the most realistic, amazing, crazy-tough woman in the world, in this character… I can't do all of that and that would be egotistical to think that I can. So, there were a few different women in this suit this year, and every one of them served a phenomenal purpose to make Bo as fierce as possible.” However, there’s one move that Sackhoff perfected. “I have a wicked knee slide! Every once in a while, when I can do a knee slide, I definitely do a knee slide.”" +
                "This season, Bo-Katan has entered a new phase for the character’s life. At the end of Season 2, she went to confront Moff Gideon only to have Din Djarin win the Darksaber from the former Imperial by besting him in combat. And so far, this season she’s returned to Mandalore, rescued Djarin twice, glimpsed a Mythosaur in the Living Waters, and watched her home be destroyed by the Imperial remnant. The journey led her into hiding with the Children of the Watch, Djarin’s Mandalorian covert, which accepted the two apostates.\n" +
                "\n" +
                "The story progression put Sackhoff opposite Emily Swallow’s character, the Armorer, for the first time in the series. Swallow has said she entertains herself and her colleagues between takes by coming up with her own Mandalorian spinoff reality shows like The Bachelorlorian and Pimp My Ship, two ideas that still have Sackhoff giggling months later. “We all spend a lot of time together, and we put our heart and soul into this. It's a lot of long hours. I'm not gonna lie and say that sitting in a suit for 16 hours a day is comfortable. So you do things to sort of keep morale up. The crew's working super hard and, and you know, Emily is definitely the person who usually makes people laugh.”" +
                "That levity is welcome both for the cast and crew laboring on set and for Sackhoff, delving into the emotional depths of Bo-Katan’s state of mind. “This is a woman who has had a lot of challenges in her life, a lot of heartache and disappointments,” Sackhoff says of Bo-Katan’s story in The Clone Wars and Star Wars Rebels. “She's always done what she thought was right for the Mandalorian people…And she's made a lot of mistakes in the process. I think that she wears all of that and there's so much guilt and so much turmoil. That plays itself out a little bit this season. For her to not take the Darksaber [in Season 2 is] such a huge moment.” Clearly, something has changed for Kryze. “Something's different, you know? There's a reason why she didn't, and I think it lends itself to who she has become and where she's going.”\n" +
                "\n" +
                "And the fans have embraced Sackhoff and Bo-Katan’s joint leap to Star Wars live action. After the surprise debut in The Mandalorian, Sackhoff delighted in watching fan reactions online. “One of my favorite things to do last year — I mean, I didn't do it like repeatedly, that would be kind of weird, — but I watched the fan reaction compilation that was put together of Bo showing up, and it just warmed my heart,” she says. “I was so happy that she was so well received. I mean, listen: love her, hate her. You can hate her. She's done some pretty appalling things in her existence. But the reaction and overall excitement — and to see people instantaneously know who she was, the moment she landed… was really cool. I loved it.”" +
                "As Bo-Katan’s journey continues, Sackhoff is tight-lipped about what’s in store, confirming only this: she did learn a new stunt move for Season 3. “But if I told you about it, it would give things away,” she says with a laugh. “That's one of the fun things. There's always a skill that I'm relearning and a different type of fighting style for different characters. Bo has a finesse to her.”");
        news3.setTitle("“As Fierce as Possible”: Katee Sackhoff on the Evolution of Bo-Katan Kryze");
        news3.setPostDate(LocalDateTime.of(2023, 12, 7, 9, 30));
        news3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));

        Picture picture3 = new Picture();
        picture3.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680197370/As_Fierce_as_Possible_ixrum0.webp");
        picture3.setPublicId("As_Fierce_as_Possible_ixrum0");
        picture3.setTitle("“As Fierce as Possible”: Katee Sackhoff on the Evolution of Bo-Katan Kryze");
        picture3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture3.setNews(news3);

        newsRepository.save(news3);
        pictureRepository.save(picture3);


        News news4 = new News();
        news4.setApproved(null);
        news4.setDescription("It’s immediately apparent that Jon Favreau and Dave Filoni have worked together off and on for more than 15 years as they settle in for a joint interview ahead of The Mandalorian’s Season 3 premiere on Disney+.\n" +
                "\n" +
                "Sometimes Favreau, the creator and executive producer of the series, begins a sentence and Filoni, also an executive producer, finishes the thought. They often erupt into laughter, in awe of their good fortune and a collaboration that began at Skywalker Ranch when Favreau was mixing Iron Man, the film that would launch the Marvel Cinematic Universe, and Filoni was working on the first season of Star Wars: The Clone Wars. “We're fortunate, you know,” Filoni tells StarWars.com. “Between Jon and I, we get along like we're playing with our old Kenner toys.”" +
                "Once again, Din Djarin and Grogu are on a quest, this time to Mandalore. And the behind-the-scenes makers, Favreau and Filoni, have dug into their creative toy chest to add new species and characters like the Pirate King Gorian Shard and evolve familiar characters by giving Bo-Katan a new throne on Kalevala. They’ve also chosen some deep-cut characters to add to the mix — including a return to Tatooine to pick up R5-D4, the same droid whose bad motivator put R2-D2 on course for delivering Leia’s urgent message to Luke Skywalker in Star Wars: A New Hope.\n" +
                "\n" +
                "“We’re in this time period post-Return of the Jedi, that in some ways we think of as all the way up to Episode VII [Star Wars: The Force Awakens],” Filoni says. “You always move between big known things in the cinematic world and we plot those out. We discuss groups. We discuss characters. We discuss who should come into the story or who should not.” Luke Skywalker and Artoo previously joined the fray in the epic Season 2 finale, a surprise that was concealed even in concept art as the proposed return of Plo Koon. “And obviously, by the third season, we have a lot of people in play already.” " +
                "To map it all out, including the various Mandalorian factions glimpsed in the trailer, the creators rely on whiteboards. “I love it. You dry-erase and — boom! — gone,” Filoni says. “It's a lot of meticulous whiteboarding and figuring out time periods and timelines and stories that have something to do with what we're doing and stories that don't. It's all valuable to us to understand what's going on in the greater galaxy, so you understand the history of the period, which informs our characters.”" +
                "“It was pretty fresh snow for a while, you know?” adds Favreau. “You had the clues from what we knew from the movies that came before this. We know what's happening after and we knew The Clone Wars. And then there's even EU material — Legends — that have been embraced by the fans. So, we want to account for everything and take everything into consideration.” That includes their own secret pieces of the puzzle, the forthcoming Ahsoka series and Skeleton Crew, in addition to The Book of Boba Fett, which directly intersected with the Mandalorian’s journey and reunited Din Djarin and Grogu. “We have other stories that are taking place that we want to make sure are consistent. And Dave's really the pivot point for all of it.” " +
                "The production is famously guarded, having successfully kept the existence of Grogu a secret from the public until he peeked out of a hovering pram in the series premiere. And Favreau feels a profound responsibility as the creator of the series. “You have a responsibility for Star Wars, to George [Lucas] and to Kathy [Kennedy] and to the people who brought me in,” Favreau says. “And the audience. They're invested in these characters. And you have young people who are growing up with the characters and what they represent. You feel very protective of that, and trying to hold as high of a standard as you can. I think those moments — the Baby Yoda reveal was very organic to the story. And just the idea of surprising to make it a bit of a left turn to people's expectations that this was going to be a guy runnin' and gunnin' through the galaxy. That was a nice twist. And I'm really happy that [everyone] supported us keeping that a secret. We put the audience experience first there.”\n" +
                "\n" +
                "Grogu — or Baby Yoda, BY, The Child, and Baby G among other nicknames given by fans and crew alike — was canonically named by Favreau, similar to the way one might christen their new puppy.\n" +
                "\n" +
                "“He’s cute, but he's a little ugly. He's ugly-cute,” Favreau says, eliciting the interviewer’s defense of his adorable nature, to which Favreau quickly concedes. “He's adorable because he's not perfect. He's got the cheeks, but he's got like weird little pointy teeth and he's got weird peach fuzz. In our design, when he looked too cute, he didn't look right. We found that right balance.”" +
                "Filoni directed the Season 2 episode, “Chapter 13: The Jedi” that first revealed Grogu’s name to the world, thanks to Ahsoka Tano. “It was good to have Ahsoka there cause she's a good balancing point,” Filoni says. “Mando at first reacted like the audience. ‘Grogu? That's strange.’ But when he says it, Grogu looks at him. And I always related that to when I have a dog and the dog starts to learn its name. You say its name, it looks at you, it's the most heartwarming thing. And so when you see that he recognizes his name and that until Ahsoka says his name, that he probably hasn't heard it in a long time — it's like, how could you reject that?”" +
                "“That’s the thing with Grogu,” Favreau adds. “He's like part dog, part human, part reptile. He's a big mixture of everything…And also as he gets older, he won't outgrow the name. It's not a cute little name and then, you know, he's as old as Yoda and he still has a weird, cute name,” Favreau says with a laugh. “‘Sparky.’ You know, you don't want to be ‘Sparky’ and be like 600 years old. It was also hard because no matter what we named him, it wasn't going to be — it couldn't be — Baby Yoda.”" +
                "“You're telling the story you're telling”\n" +
                "\n" +
                "As the next chapters in the critically-acclaimed series unfold, no one beyond the core production cast and crew know what surprises are in store and where the story is headed next. " +
                "“We know that the Mandalorian had removed his helmet and, instead of being written off completely, it's been learned that if he could return to Mandalore and bathe in the Living Waters, that he could be purified and forgiven,” Favreau says, a journey that takes us to “Chapter 18: The Mines of Mandalore.” “We know that Bo-Katan wanted to go back there, too, but now he has the Darksaber, which is something that she had wanted.”\n" +
                "\n" +
                "“It's like I'm watching the show now,” Filoni interjects with a laugh.\n" +
                "\n" +
                "“We have a lot of trajectories that are lined up and then we have different factions of Mandalorians, right?” Favreau continues. “And the resurgent Empire is out there somewhere and we know that that's growing. So as we come into Season 3, we know that all of these different intersecting vectors are happening. We’re going to see the relationship evolve between our two leads.”" +
                "Favreau and Filoni are surprisingly insulated from the effects of helming a global phenomenon, a skill that Filoni has honed over 15 years of working in the Star Wars galaxy. “You know, George [Lucas] taught me, you're telling the story you're telling,\" Filoni says when asked if the series’ success impacts his creative process now. \"And I think we're incredibly lucky that people have responded the way they do. I learned a long time ago, people just don't like this because you stick the name Star Wars on it. That's not good enough. You have to care. You have to put a lot of work into it.” That starts with Favreau and Filoni, and their creative toy box and whiteboards, and ripples out to the rest of the cast and crew. “They love it. They research things. They find the right items to make the props. They put the extra time in to make the costumes. You know, Kathy Kennedy, she was adamant about the level of visual effects that this thing needed to have there. It has to be equal to a movie. Everybody invests in this. And so, you know, we are delivering something that we can be proud of and that we like as fans. At the end of the day, you can only make something that you really enjoy and hope that other people like it. But the response to Grogu, when people saw him and that was on a level that even though I'd been with Star Wars a while, I had never experienced anything like it. I think that's a once-in-a-lifetime thing. You're lucky if you're ever a part of something like that. If anything, it puts more pressure on you because you know how many eyes are watching this. The expectation has grown. But it's a privilege to have that.”" +
                "Favreau has been a part of the galaxy almost as long, first stepping into the role of Pre Vizsla in Star Wars: The Clone Wars, the leader of Death Watch who wielded the Darksaber in animation. “Life is weird,” he says. “In retrospect, it looks like it has followed some path. But at the time it just felt like we were doing things that seemed fun and cool and I had no idea [it would lead to The Mandalorian]. I definitely feel a connection to the Darksaber because I was the first one to wield it.” The idea for the Darksaber came from Lucas himself, a rewrite after Favreau initially recorded the episode with dialogue for a vibroblade. “George didn't like the logic of vibroblade being able to parry a lightsaber, so he invented the Darksaber, and I had to read the whole monologue about how it was found in a Jedi Temple. And I was telling my wife, she's like, ‘That's the coolest thing in the world.’ And it became so cool that we ended up making a whole TV show about it,” he says with a laugh. “That whole idea and how profound that thing is, that Tarre Vizsla was both Mandalorian and Jedi, that implies so much because those were two diametrically opposed warring factions. So, what does that mean? These are the clues that we look for, these little anomalies and things that instead of shying away from, we sort of delve into and explore.” That includes the factions of Bo-Katan’s Mandalorian sect and the Children of the Watch, whose members must never remove their helmets. “In The Clone Wars, they're always taking their helmets off. So what's going on there?” Favreau asks. “We pulled all that together and in Season 2 they confronted each other. Now, we see how all of that plays out.”" +
                "As for the best-kept-secrets and character surprises that the show has become known for, we’ll have to wait and see what comes next. “We like to have surprises,” Favreau says. “We like to have something revealed each episode. I like when everybody tries to see it at the same time and then talks about it and reacts to it, discusses it. Tries to guess what's happening next. That's part of the fun of the serialized storytelling. We want to make sure that each week a new piece of information comes out and that maybe things resolve in an unexpected way, but ways that feel justified and emotionally resonant and character-based. Look, we just hope that people enjoy this new set of stories. I certainly am enjoying doing what I'm doing here. Hopefully we can do it for a long time.”");
        news4.setTitle("Jon Favreau and Dave Filoni Chart a Course to Mandalore");
        news4.setPostDate(LocalDateTime.of(2023, 12, 7, 8, 30));
        news4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));

        Picture picture4 = new Picture();
        picture4.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680197883/Jon_Favreau_and_Dave_Filoni_Chart_a_Course_to_Mandalore_zr0zqs.webp");
        picture4.setPublicId("Jon_Favreau_and_Dave_Filoni_Chart_a_Course_to_Mandalore_zr0zqs");
        picture4.setTitle("Jon Favreau and Dave Filoni Chart a Course to Mandalore");
        picture4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture4.setNews(news4);

        newsRepository.save(news4);
        pictureRepository.save(picture4);


        News news5 = new News();
        news5.setApproved(null);
        news5.setDescription("Today at San Diego Comic-Con 2023, Massive Entertainment, Ubisoft, and Lucasfilm Games held a panel discussion about the highly-anticipated Star Wars Outlaws. Notably, a behind-the-scenes feature debuted, showing new visuals from the game, concept art, and insights from the cast and creators. Watch it below" +
                "The video offers first looks and information about many areas of the game, including background around how the time period and themes were selected. Julian Gerighty, creative director, also details how Outlaw’s premise influenced the worlds and elements created for the game, “Building the world of Star Wars Outlaws,\" he says, \"first thing we had to do was think of places that outlaws thrive: these hubs of scum and villainy.”\n" +
                "\n" +
                "The feature goes on to describe the creation of Toshara, a new moon, and its main city of Mirogana, to support the outlaw fantasy Massive is bringing to life. It also delves into how the team is drawing from Westerns for the game’s tone, and the syndicates and scoundrel experiences players will encounter. “We’ve only scratched the surface of what Star Wars Outlaws has to offer,” says Lucasfilm’s Steve Blank, director of franchise content and strategy, to close out the feature. “There is so much more that you will get to embody and use and play when you become Kay Vess.”\n" +
                "\n" +
                "For more on Star Wars Outlaws, check out StarWars.com’s new interview with Julian Gerighty.");
        news5.setTitle("SDCC 2023: Take a Peek Behind the Scenes of Star Wars Outlaws");
        news5.setPostDate(LocalDateTime.of(2023, 12, 7, 13, 30));
        news5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));

        Picture picture5 = new Picture();
        picture5.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1692084354/l8k9rfdgu2ssufj1ic6v.jpg");
        picture5.setPublicId("l8k9rfdgu2ssufj1ic6v");
        picture5.setTitle("Star Wars Outlaws");
        picture5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture5.setNews(news5);

        newsRepository.save(news5);
        pictureRepository.save(picture5);


        News news6 = new News();
        news6.setApproved(null);
        news6.setDescription("Andor, Obi-Wan Kenobi, The Mandalorian, and Light & Magic have been honored with 23 nominations in 21 different categories at the 75th Primetime Emmy® Awards. Among the top honors, Andor was nominated in the Outstanding Drama Series category with a total of 8 nominations, Obi-Wan Kenobi nabbed a nomination in the Outstanding Limited or Anthology Series category and a total of 5 nominations, and The Mandalorian was nominated for a total of 9 nominations. \n" +
                "\n" +
                "\"This morning the 2023 Emmy nominations were announced and I am thrilled to share that a record four of our productions – The Mandalorian, Andor, Obi-Wan Kenobi, and Light & Magic – have received an outstanding combined 23 nods across 21 categories,\" Lucasfilm President Kathleen Kennedy said in a message to employees. \"I am so proud of everyone who worked on these productions and the acknowledgement each one of them has so dearly earned for their dedication and hard work.\" \n" +
                "\n" +
                "Read the full list of Star Wars nominations below." +
                "Outstanding Drama Series\n" +
                "\n" +
                "Andor • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Outstanding Limited or Anthology Series\n" +
                "\n" +
                "Obi-Wan Kenobi • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Outstanding Cinematography For A Series (Half-Hour)\n" +
                "\n" +
                "The Mandalorian • “Chapter 20: The Foundling” • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Dean Cundey, ASC, Director of Photography\n" +
                "\n" +
                "Outstanding Cinematography For A Series (One Hour)\n" +
                "\n" +
                "Andor • “Rix Road” • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Damián García, Director of Photography\n" +
                "\n" +
                "Outstanding Fantasy/Sci-Fi Costumes\n" +
                "\n" +
                "The Mandalorian • “Chapter 22: Guns For Hire” • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Shawna Trpcic, Costume Designer\n" +
                "\n" +
                "Elissa Alcala, Assistant Costume Designer\n" +
                "\n" +
                "Julie Robar, Costume Supervisor\n" +
                "\n" +
                "Julie Yang Silver, Costume Supervisor\n" +
                "\n" +
                "Obi-Wan Kenobi • “Part I” • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Suttirat Anne Larlarb, Costume Designer\n" +
                "\n" +
                "Stacia Lang, Assistant Costume Designer\n" +
                "\n" +
                "Lynda Foote, Costume Supervisor\n" +
                "\n" +
                "Outstanding Directing For A Drama Series\n" +
                "\n" +
                "Andor • “Rix Road” • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Benjamin Caron, Directed by\n" +
                "\n" +
                "Outstanding Picture Editing For A Limited Or Anthology Series Or Movie\n" +
                "\n" +
                "Obi-Wan Kenobi • “Part VI” • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Kelley Dixon, ACE, Editor Josh Earl, ACE, Editor\n" +
                "\n" +
                "Outstanding Period And/Or Character Hairstyling\n" +
                "\n" +
                "The Mandalorian • “Chapter 19: The Convert” • Disney+ • Lucasfilm Ltd.\n" +
                "\n" +
                "Maria Sandoval, Hair Designer\n" +
                "\n" +
                "Ashleigh Childers, Key Hairstylist\n" +
                "\n" +
                "Sallie Ciganovich, Hairstylist ");
        news6.setTitle("Lucasfilm and Star Wars Receive a Combined 23 Emmy Nominations");
        news6.setPostDate(LocalDateTime.of(2023, 12, 7, 15, 30));
        news6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));

        Picture picture6 = new Picture();
        picture6.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1692084626/iyelij9u2qlzpgxdd9yl.jpg");
        picture6.setPublicId("iyelij9u2qlzpgxdd9yl");
        picture6.setTitle("Star Wars Receive a Combined 23 Emmy Nominations");
        picture6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture6.setNews(news6);

        newsRepository.save(news6);
        pictureRepository.save(picture6);


        News news7 = new News();
        news7.setApproved(null);
        news7.setDescription("Lucasfilm and The Walt Disney Company are thrilled and humbled to announce that Part One of Ahsoka, \"Master and Apprentice,\" was the most-watched title on Disney+ this past week. The debut episode garnered 14 million views, making the series number one globally on the streaming platform.\n" +
                "\n" +
                "“Ahsoka has become a fan favorite with people of all ages and it’s wonderful to see her continue to resonate with viewers in her very own headlining series,” says Kathleen Kennedy, Lucasfilm president. “I want to recognize the fantastic work done by our creative team, led by Dave Filoni and Jon Favreau, the incredible cast led by Rosario Dawson, and our talented crew — and on behalf of the team and all of Lucasfilm, we give our thanks to all the fans who have been with Ahsoka on every step of her journey and to all those who are just learning about her now in Ahsoka on Disney+.”\n" +
                "\n" +
                "New episodes of Ahsoka arrive every Tuesday at 6 p.m. PT., only on Disney+");
        news7.setTitle("Ahsoka Draws 14 Million Views for First Episode");
        news7.setPostDate(LocalDateTime.of(2023, 12, 7, 17, 45));
        news7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));

        Picture picture7 = new Picture();
        picture7.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1692084135/ni8r27o0ii9lmpmo7czx.jpg");
        picture7.setPublicId("ni8r27o0ii9lmpmo7czx");
        picture7.setTitle("Ahsoka Draws 14 Million Views for First Episode");
        picture7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture7.setNews(news7);

        newsRepository.save(news7);
        pictureRepository.save(picture7);

    }
}
