#include <bits/stdc++.h>

using namespace std;

struct Job {
    // 작업 번호, 요청 시각, 소요시간
    int number, request_time, job_length;
};

struct Compare {
    bool operator()(const Job& a, const Job& b) {
        if (a.job_length == b.job_length) {
            if (a.request_time == b.request_time) {
                return a.number > b.number;
            }
            return a.request_time > b.request_time;
        }
        return a.job_length > b.job_length;
    }
};

int solution(vector<vector<int>> jobs) {
    int answer = 0;
    /**
    *   Non-preemptive한 스케줄링이므로, 우선순위 큐를 활용해 주어진 조건에 따라 디스크 작업을 정렬한다.
    *   우선순위 큐에 모든 값을 넣게 되면, 아직 요청이 들어오지 않은 시간에 미래의 디스크 작업이 처리되므로 이를 순차적으로 넣어줘야 한다.
    */
    
    // 요청시각 순으로 디스크 작업을 정렬하여, 각 시간에 따라 작업 요청이 이루어진 작업만 우선순위 판별에 사용되도록 한다.
    vector<Job> jobs_v;
    for (int i = 0; i < jobs.size(); i++) {
        auto& job = jobs[i];
        int request_time = job[0], job_length = job[1];
        
        jobs_v.push_back(Job(i, request_time, job_length));
    }
    sort(jobs_v.begin(), jobs_v.end(), [](const auto& a, const auto& b) {
        if (a.request_time == b.request_time) {
            if (a.job_length == b.job_length) {
                return a.number < b.number;
            }
            return a.job_length < b.job_length;
        }
        return a.request_time < b.request_time;
    });
    
    priority_queue<Job, vector<Job>, Compare> pq;
    pq.push(jobs_v[0]);
    int time = jobs_v[0].request_time, idx = 1;
    while (!pq.empty() || idx < jobs_v.size()) {
        // 직전 작업의 종료시간보다 늦은 요청시각의 작업이 남아있을 경우, pq에 밀어넣고 시간을 건너뛴다.
        if (pq.empty() && idx < jobs_v.size()) {
            time = jobs_v[idx].request_time;
            pq.push(jobs_v[idx++]);
        }
        
        Job job = pq.top();
        pq.pop();
        
        time += job.job_length;
        answer += time - job.request_time;
        
        cout << job.number << ": " << time << '\n';
        
        // 한 디스크 작업이 수행된 후, request_time이 time보다 작아진 작업을 우선순위 큐에 밀어넣는다.
        while (idx < jobs_v.size() && jobs_v[idx].request_time <= time) {
            pq.push(jobs_v[idx++]);
        }
    }
    answer /= jobs.size();
    
    return answer;
}