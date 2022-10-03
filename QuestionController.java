package com.javabykiran.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.javabykiran.entity.Answer;
import com.javabykiran.entity.Questions;
import com.javabykiran.entity.Score;

@Controller
public class QuestionController {

	@Autowired
	SessionFactory factory;
	
	@RequestMapping("getQuestions")
	public ModelAndView getQuestions(String subject,HttpServletRequest request)
	{
		System.out.println(subject);
		
		Session session = factory.openSession();
		
		Query query = session.createQuery("from Questions where subject=:subject");
		query.setParameter("subject",subject);
		
		List<Questions> listOfQuestions=query.list();
		
		System.out.println(listOfQuestions);
		
		HttpSession httpSession = request.getSession();
		
		httpSession.setAttribute("listOfQuestions",listOfQuestions);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("questions");
		
		return modelAndView;
	}
	

	@RequestMapping("startExam")
	public ModelAndView startExam(String selectedSubject,HttpServletRequest request)
	{
		if(!selectedSubject.equals("actionNoRequired"))
		{
				HttpSession httpSession = request.getSession();
				List<Questions> listOfQuestions = (List<Questions>) httpSession.getAttribute("listOfQuestions");
				
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("questionnavigation");
				modelAndView.addObject("question",listOfQuestions.get(0));
				
				httpSession.setAttribute("score",0);
				httpSession.setAttribute("qno",0);
				httpSession.setAttribute("timeremaining",301);
				
				HashMap<Integer,Answer> hashmap=new HashMap<Integer,Answer>();
				httpSession.setAttribute("submittedDetails",hashmap);
				
				httpSession.setAttribute("subject",selectedSubject);
				
				return modelAndView;
		}
		else
		{
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("questions");
			modelAndView.addObject("msg","do not click on select subject");
			return modelAndView;
		}
	}
	
	// qno=1  question=what is java? submittedAnswer="language" . This is coming from browser
	// Answer class object will be created by Spring using default constructor
	// Answer answer=new Answer();
	// [qno=0 question=null submittedAnswer=null correctAnswer=null] Answer class object
	// answer.setQno(1);
	// [qno=1 question=what is java? submittedAnswer="language" correctAnswer=null] Answer class object
	
	@RequestMapping("storeResponse")
	public void storeResponse(Answer answer,HttpServletRequest request)
	{
		//System.out.println("before getting correct answer from list " + answer);
		
		HttpSession httpSession = request.getSession();
		
		List<Questions> listOfQuestions = (List<Questions>) httpSession.getAttribute("listOfQuestions");
		
		int qnofrombrowser=answer.getQno();//6
		
		Questions expectedQuestion=null;// [(5,wha),(6,wha,),(7,wha,)] List 
		
		for(Questions question:listOfQuestions)  // find out that Question from list whose answer was submitted  
		{
			if(question.getQno()==qnofrombrowser)
			{
				expectedQuestion=question;
				break;
				
			}
		}
		
		String correctAnswer=expectedQuestion.getAnswer();
			
		answer.setCorrectAnswer(correctAnswer);
		
		//System.out.println("after getting correct answer from list " + answer);
		
		// [qno=1 question=square correctAnswer=4 option1=2 option2=3] Question object
		
		// 1. retrieve hashmap object from session
		
		HashMap<Integer,Answer> hashmapobject = (HashMap<Integer,Answer>) httpSession.getAttribute("submittedDetails");
		
		// update retrieved hashmap object
		
		hashmapobject.put(answer.getQno(), answer);
	
		// 3. add this updated hashmap object in session using setAttribute 
		
		httpSession.setAttribute("submittedDetails",hashmapobject);
	
		System.out.println(hashmapobject);
	}

	@RequestMapping("next")
	public ModelAndView next(HttpServletRequest request)
	{
		System.out.println("inside next");
		HttpSession httpSession = request.getSession();
		ModelAndView modelAndView = new ModelAndView();
		List<Questions> listOfQuestions = (List<Questions>) httpSession.getAttribute("listOfQuestions");
		
		if((Integer)httpSession.getAttribute("qno")<listOfQuestions.size()-1)
		{
			
		httpSession.setAttribute("qno",(Integer)httpSession.getAttribute("qno") + 1);
		
		Questions question = listOfQuestions.get((Integer)httpSession.getAttribute("qno"));
		
		HashMap<Integer,Answer> hashmapobject = (HashMap<Integer,Answer>) httpSession.getAttribute("submittedDetails");
		Answer answer = hashmapobject.get(question.getQno());
		String previousAnswer="";
		if(answer!=null)
			previousAnswer=answer.getSubmittedAnswer();
				
			question = listOfQuestions.get((Integer)httpSession.getAttribute("qno"));
			modelAndView.setViewName("questionnavigation");
			modelAndView.addObject("question",question);
			modelAndView.addObject("previousAnswer",previousAnswer);
		}
		
		else
		{
			modelAndView.setViewName("questionnavigation");
			modelAndView.addObject("message","questions over");
			httpSession.setAttribute("qno",listOfQuestions.size());
			
		}
		
		return modelAndView;
		
	}
	
	
	@RequestMapping("previous")//0
	public ModelAndView previous(HttpServletRequest request)
	{
		HttpSession httpSession = request.getSession();
		ModelAndView modelAndView = new ModelAndView();
		
		if((Integer)httpSession.getAttribute("qno")>0)
		{
		
			List<Questions> listOfQuestions = (List<Questions>) httpSession.getAttribute("listOfQuestions");
			
			httpSession.setAttribute("qno",(Integer)httpSession.getAttribute("qno") - 1);
			Questions question = listOfQuestions.get((Integer)httpSession.getAttribute("qno"));
		
			HashMap<Integer,Answer> hashmapobject = (HashMap<Integer,Answer>) httpSession.getAttribute("submittedDetails");
			Answer answer = hashmapobject.get(question.getQno());
			String previousAnswer="";
			if(answer!=null) {
				previousAnswer=answer.getSubmittedAnswer();
			}
		
//		if((Integer)httpSession.getAttribute("qno")>=0)
//		{
			question = listOfQuestions.get((Integer)httpSession.getAttribute("qno"));
			modelAndView.setViewName("questionnavigation");
			modelAndView.addObject("question",question);
			modelAndView.addObject("previousAnswer",previousAnswer);
		//}
		
		}
		else
		{
			modelAndView.setViewName("questionnavigation");
			modelAndView.addObject("message","click on next button");
			httpSession.setAttribute("qno",-1);
		}
		return modelAndView;
	}
	
	
	@RequestMapping("endexam")
	public ModelAndView endexam(HttpServletRequest request)
	{
		System.out.println("inside end exam");
		HttpSession httpSession = request.getSession();
		HashMap<Integer,Answer> hashmapobject = (HashMap<Integer,Answer>) httpSession.getAttribute("submittedDetails");
		
		System.out.println(hashmapobject);
		ModelAndView modelAndView=null;
		/*
		 Cannot invoke "java.util.HashMap.values()" because "hashmapobject" is null
java.lang.NullPointerException: Cannot invoke "java.util.HashMap.values()" because "hashmapobject" is null
	at com.javabykiran.controller.QuestionController.endexam(QuestionController.java:207)
		 */
if(hashmapobject!=null) {
		Collection<Answer> answers=hashmapobject.values();
		
		for(Answer answer:answers)
		{
			if(answer.submittedAnswer.equals(answer.correctAnswer))
			{
				httpSession.setAttribute("score", (Integer)httpSession.getAttribute("score") + 1);
							
			}
		}
		
		// store score in database
		
		Session hibernateSession = factory.openSession();
		
		Transaction tx = hibernateSession.beginTransaction();
		
						Score  score = new Score();
						
						score.setUsername((String)httpSession.getAttribute("username"));
						score.setSubject((String)httpSession.getAttribute("subject"));
						score.setMarks((Integer)httpSession.getAttribute("score"));
		
						hibernateSession.save(score);
				
		tx.commit();
	
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName("score");
		modelAndView.addObject("score",httpSession.getAttribute("score"));
		modelAndView.addObject("submittedDetails",httpSession.getAttribute("submittedDetails"));
		
		
		httpSession.invalidate();
}

else
{
	modelAndView = new ModelAndView();
	modelAndView.setViewName("endpage");
}
		return modelAndView;
		
		
	}
	
	@RequestMapping("questions")
	public String selectSubject()
	{
		return "questions";
	}
	
	@RequestMapping("score")
	String score(HttpServletRequest request)
	{
		HttpSession session2=request.getSession(false);
		System.out.println("Session is " + session2);
		
		if(session2==null)	
			return "login";
		
		System.out.println("inside end exam");
		HttpSession httpSession = request.getSession();
		HashMap<Integer,Answer> hashmapobject = (HashMap<Integer,Answer>) httpSession.getAttribute("submittedDetails");
		
		System.out.println(hashmapobject);
	
		
if(hashmapobject!=null) {
		Collection<Answer> answers=hashmapobject.values();
		
		for(Answer answer:answers)
		{
			if(answer.submittedAnswer.equals(answer.correctAnswer))
			{
				httpSession.setAttribute("score", (Integer)httpSession.getAttribute("score") + 1);
							
			}
		}
		
		// store score in database
		
		Session hibernateSession = factory.openSession();
		
		Transaction tx = hibernateSession.beginTransaction();
		
						Score  score = new Score();
						
						score.setUsername((String)httpSession.getAttribute("username"));
						score.setSubject((String)httpSession.getAttribute("subject"));
						score.setMarks((Integer)httpSession.getAttribute("score"));
		
						hibernateSession.save(score);
				
		tx.commit();
	
		
		
		request.setAttribute("score",httpSession.getAttribute("score"));
		request.setAttribute("submittedDetails",httpSession.getAttribute("submittedDetails"));
		
			
		
		httpSession.invalidate();
}


		
		return "score";//httpSession.getAttribute("score")
	}
	
}



