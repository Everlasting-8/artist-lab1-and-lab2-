# Дневник разработки веб-приложения "Интернет-Магазин"
Главная цель: Разработать простое веб-приложение -- интернет-магазин "Аптека-Лайн".

## TO-DO
* (Требование) Написать RestAPI CRUD-контроллеры для каждой главной сущности
* (Требование) Покрыть тестами каждый контроллер через testcontainers и jupiter
* (Требование) Приложение должно запускаться в докере через docker-compose
* (Требование) Должна быть авторизация с уникальными действиями для каждой роли
* (Требование) Каждый findAll должен иметь пагинацию
* (Требование) Должен быть минимум один запрос, который вернет findAll в виде бесконечной прокрутки без указания общего количества записей
* (Требование) Должен быть минимум один запрос, который вернет findAll с пагинацией и с указанием общего количества записей в http хедере
* (Требование) На сложных запросах должны использоваться транзакции. Должно быть не меньше двух подобных запросов. Обосновать почему там нужны тразакции

Каким оно должно быть?
Это простой многостраничник с авторизацией (без регистрации). Новых пользователей может создавать только супервайзер.
Есть каталог продукции, можно оформлять заказы, просматривать историю заказов, создавать\удалять пользователей.
Есть три типа пользователей (три роли):
1. Супервайзер. Доступна вся функциональность. Только он может добавлять и удалять пользователей.
2. Поставщик. Может только изменять каталог продукции.
3. Покупатель. Может просматривать каталог, оформлять заказы, просматривать историю заказов.

Интернет-магазин продаёт лекарства и рецепты.
Рецепт состоит из n-ного кол-ва определённых лекарств (или продуктов).
Каталог и витрина это одно и то же.
Каталог состоит из n позиций.
Позиция это выставленный на витрину рецепт.
Позиции формируются Поставщиком, он может выставлять на витрину новые позиции и снимать старые.

Диаграмма главных сущностей:
classDiagram

Order "*" --> "*" Position
Position "*" --> "1" Recipe
Recipe "*" <--> "*" Product  
Role "*" <--> "1" User

Главные задачи:
* Модель code-first с liquibase.
    Done. Сначала пишу все нужные entity-классы, затем запускаю 
      ``mvn liquibase:generateChangeLog``
    Только предварительно заменяю yaml файл с журналом изменений на новый в pom.xml чтобы старый не перетёрся.
    Или может не надо? Всё и так будет работать?
* Контроллеры с тестами, транзакциями, пагинацией.
    Так, может тут лучше сгенерировать crud-контроллер через openApi?
    Долго писать свои...
* Авторизация с ролями и JWT
    DONE. Ужас, ушёл весь день чисто на эту хрень...
* Докер

Задачи разбиваю по веткам-фичам.

7.9. 10:14.
Теперь надо писать crud-контроллеры. Деление по ролям такое:
* Супервайзер имеет доступ ко всему
* Поставщик имеет доступ только к методам из Position, Recipe, Product
* Покупатель может только создавать и просматривать Orders

До конца осталось 1 день 8 часов.

Сначала пишу спецификацию OpenAPI, по ней генерирую контроллеры. Хотя нафиг этот опенапи, двойная работа.
Сначала надо создать Entity.

13:23
    Создал все entity, сгенерил changelog. Теперь омжно писать контроллеры.
    
8.9. 14:08
Надо поправить роли у эндпоинтов, ещё:
* Создать 2 сложных эндпоинта с транзакциями
* Написать 2 findAll с пагинацией
* Покарыть тестами каждый контроллер через testcontainer
* Создать docker-image, запустить через docker-compose

13.11
Пришли корректировки:
2. Не используйте @Autowired. Вместо этого попробуйте Constructor Injection (with @RequiredArgsConstructor lombok) 
Решение: Везде убрать @Autowired, заменить на @RequiredArgsConstructor, проверить что всё работает. Может полететь
авторизация...
3. я хочу видеть связи между сущностями. Например повар готовит еду, еда готовится по рецепту и со склада тратятся
   продукты.
4. Хочу видеть @Transactional
Решение: Связать 3 и 4, сделать эндпоинт создания заказа покупателем. Покупатель набирает товаров в корзину, оформляет
заказ. В системе убавляется кол-во купленного товара, выставляется счёт и т.п, всё в одной транзакции.
5. Не вижу чтобы вы решали N + 1 Problem. Есть родительская и дочернаяя таблицы Parent (id, name) и Child (id,
   parent_id, name). Нужно достать из БД инфу о всех детях и их родителях. Обычно это деляется простым джоином (select
   c.name, p.name from child c join parent p on p.id = c.parent_id). Однако из-за недостатков ORM или неправилоного
   программирования, возможна ситуация когда инфа будет доставаться так: (select * from child c), (select * from parent
   p where p.id = c[1].parent_id) ... (select * from parent p whre p.id = c[n].parent_id), т.е. будет выполнено n + 1
   ходок в БД вместо одной.
Проверить связки @OneToMany между таблицами на возможную n+1, чинить ручным переписыванием запросов через join fetch.
6. Покажи мне пагинацию (Pagination). Запилить пагинацию на GET /order
