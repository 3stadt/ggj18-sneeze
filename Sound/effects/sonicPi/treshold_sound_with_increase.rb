

bass_e = [52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65]
bass_a = [57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70]
bass_d = [62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75]
bass_g = [67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80]


define :mainriff do |sleeper|
  use_synth :chipbass
  #with_fx :flanger,  delay: 0.1 do
  
  play bass_e[1]
  sleep sleeper
  play bass_a[0]
  sleep sleeper
  play bass_a[3]
  sleep sleeper
  play bass_a[4]
  sleep sleeper
  play bass_a[5]
  sleep sleeper
  play bass_a[5]
  sleep sleeper
  play bass_e[5]
  sleep sleeper
  play bass_e[4]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_a[3]
  sleep sleeper
  play bass_a[3]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_e[4]
  sleep sleeper
  play bass_e[5]
  sleep sleeper
  play bass_e[5]
  sleep sleeper
  play bass_a[5]
  sleep sleeper
  play bass_e[5]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_a[3]
  sleep sleeper
  play bass_a[3]
  sleep sleeper
  play bass_e[1]
  sleep sleeper
  play bass_a[5]
  sleep sleeper
  play bass_e[3]
  sleep sleeper
  play bass_a[3]
  sleep sleeper
  #end
end




sleeper= 0.35

live_loop :mainy do
  1.times do
    mainriff(sleeper)
  end
  sleeper = sleeper*0.95
  
end

in_thread do
  sample :vinyl_hiss, amp:1.4
end

live_loop :bassdrum do
  sample :bd_ada
  sleep sleeper*2
end


live_loop :snare do
  sample :drum_snare_soft, amp:0.8
  sleep sleeper*4
end



#Anfangs: Sweetline
#while game: Puffer 7
#Panik am Ende: mainriff


# mehr kranke