
define :wrong_healing do
  start = 50
  sample :ambi_lunar_land , rate: 10, start: 0.3 ,  release: 0.1, amp: 0.1
  sample :ambi_glass_hum, rate: 4 , release: 0.1, amp: 0.3
  play start, amp: 3,  release: 0.3
  play start-12, amp: 3,  release: 0.2
  sleep 0.2
  play start-2, amp: 3,  release: 0.4
  play start-14, amp: 3,  release: 0.4
  
end


wrong_healing

