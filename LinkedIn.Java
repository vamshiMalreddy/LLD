/*
 * Job postings
 * Users
 * 
 * User profile management, experience, education and relevant details
 * Recruiters can post a job
 * Users can apply for jobs
 * Searching for jobs 
 * Connect users with each other
 * Messaging between users
 * notify users on connection request and messages
 */

 User{
    email
    password
    profile

    List<User> connections;
 }

 Recruiter extends User{

 }

 Organization

 Job{
    Organization
    roleName
    jobDescription
    requirements
    List<Application> applications;
    String location
 }

 Message{
    User sender;
    User reciever;
    String messageContent;
    Date timeStamp;
 }

 Conversation{
    List<Message> messages;
    User user1;
    User user2;
 }

 Notification{
    NotificationType type;
    String content;
    String recepientId;
    String senderId;
    Date timestamp;
 }


 Application{
    String resume
    Profile userProfile
 }

 Profile{
    List<Experience> experiences
    List<String> skills
    List<Education> educations
    ..
 }

 Experience{
    Organization
    duration
    startDate
    endDate
    roleName
 }

 Education{
    schoolName
    graduationYear
    startYear
    degree
 }


 interface RealTimeNotificationService{}
 interface NotificationRepo{}

 // Is this enough?
 NotificationService{
    NotificationRepo notificationRepo;

    sendNotification(String content,String recepientId, String senderId, NotificationType type){
        Notification notificaion = new Notification(content,recepientId, senderId, type);
        NotificationRepo.save(notificaion);
        RealTimeNotificationService.sendRealTimeNotification(notification);
    }
 }

 MessagingService{
    MessageRepo messageRepo;

    sendMessage(User sender, User reciever, String messageContent){
        // create conv is absent
        String notificationContent = "You have a new message from User " + senderId + ".";
        NotificationService.sendNotification(notificationContent, requestedBy.getUserId,...)
    }
 }

 public interface JobRepo{
    List<Job> getAllJobs();
    List<Job> getAllJobsByQuery(JobSearchQuery jobSearchQuery);
 }

 public class InMemoryJobRepo implements JobRepo{
    List<Job> jobs;

    List<Job> getAllJobsByQuery(JobSearchQuery jobSearchQuery){
        return jobs.stream()
                .filter(job -> jobSearchQuery.getJobRole() == null || job.getJobeRole.contains(jobSearchQuery.getJobRole()))
                .filter(job -> jobSearchQuery.getLocation() == null || job.getLocation.equalsIgnoreCase(jobSearchQuery.getLocation()))
                .filter(job -> jobSearchQuery.getCompanyName() == null || job.getCompanyName.equalsIgnoreCase(jobSearchQuery.getCompanyName()))
                .filter(job -> jobSearchQuery.getSkillsRequired() == null || jobSearchQuery.getSkillsRequired().stream().allMatch(skill -> job.getSkillsRequired().contains(skill)))
    }
 }

 JobService{
    JobRepo jobRepo;

    Job createJobPosting(Recruiter recruiter, Organization, roleName, jobDescription, requirements)
    Application applyForJob(User user, Job jobPosting)
 }

 
 JobSearchQueryParams{
    String companyName;
    String location;
    String jobRole;
    String jobtype;
    List<String> skillsRequired;
 }

 JobSearchService{
    JobRepo jobRepo;
    public List<Job> searchJob(JobSearchQueryParams jobSearchQueryParams){
        return jobRepo.getAllJobsByQuery(jobSearchQueryParams);
    }
 }

 ConnectionRequest{
    User requestedBy;
    User requestTo;
    ConnectionStatusEnum status (ACCEPTED, PENDING, REJECTED);
 }



 ConnectionService{
    ConnectionRepo connectionRepo;

    makeConnectionRequest(User requestedBy, User requestTo){
        // create new connection
        // notify
        String notificationContent = "You have a new message from User " + senderId + ".";
        NotificationService.sendNotification(notificationContent, requestedBy.getUserId,...)
    }

    acceptConnection(ConnectionRequest){
        ConnectionRequest.getRequestedBy().addConnection(ConnectionRequest.requestTo())
        ConnectionRequest.requestTo().addConnection(ConnectionRequest.getRequestedBy())
    }
    rejectConnection(ConnectionRequest)
 }



