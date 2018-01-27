
define :healing do
  sample :ambi_lunar_land , rate: 10, start: 0.3 ,  release: 0.1, amp: 0.3
  sample :ambi_glass_hum, rate: 4 , release: 0.1
  play 100, amp: 0.3, attack: 0.3, release: 0.2
  sleep 0.1
  sample :ambi_glass_hum, rate: 6, release: 0.5
end


healing

