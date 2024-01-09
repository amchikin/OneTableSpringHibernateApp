package ru.chikin.springcourse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.chikin.springcourse.models.Person;

import java.util.List;


@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true) // Аннотация для открытия транзакции; readOnly показывает, что функция ничего не меняет, а только показывает
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession(); // получили сессию

        return session.createQuery("select person from Person person", Person.class)
                .getResultList(); // создали запрос через сессию на отображение всех людей и засунули это в List
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession(); // получили сессию
        return session.get(Person.class, id); // вернули человека через сессию по id
    }
    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession(); // получили сессию
        session.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession(); // получили сессию
        Person personToBeUpdate = session.get(Person.class, id);
        personToBeUpdate.setName(updatedPerson.getName());
        personToBeUpdate.setAge(updatedPerson.getAge());
        personToBeUpdate.setEmail(updatedPerson.getEmail());
    }
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession(); // получили сессию
//        Person personToBeDelete = session.get(Person.class, id);
//        session.remove(personToBeDelete);
        session.remove(session.get(Person.class, id));// объеденили две стороки выше в одну
    }
}
