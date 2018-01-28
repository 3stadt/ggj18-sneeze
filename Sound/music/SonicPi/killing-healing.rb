
define :healing do
  sample :ambi_lunar_land , rate: 10, start: 0.3 ,  release: 0.1, amp: 0.3, pan:-1
  sample :ambi_glass_hum, rate: 4 , release: 0.1, pan:1
  play 100, amp: 0.3, attack: 0.5, release: 0.2
  sleep 0.1
  sample :ambi_glass_hum, rate: 6, release: 0.5
end


define :killing do
  in_thread do
    
    with_fx :reverb do
      with_synth :hoover do
        sample :bd_haus, release: 0.4, sustain:0.5, amp:0.2
        sleep 0.03
        sample :bd_tek, release: 0.4, sustain:0.5, amp:0.2
      end
    end
    
    start_tone = 48
    volume = 0.2
    with_fx :reverb do
      with_synth :hoover do
        play start_tone, release:0.2 , pan:-1, amp: volume
        sleep 0.4
        play start_tone-2, release:0.2, pan:-0.5, amp: volume
        sleep 0.4
        play start_tone-4, release:0.2, pan:0.5, amp: volume
        sleep 0.4
        play start_tone-7, release:0.2, pan:1, amp: volume
        sleep 0.4
      end
      
    end
    
  end
  sample :ambi_lunar_land , rate: 1,  release: 0.2, amp: 0.1 ,start: 0.3, finish: 0.45, pan:-1
  sample :ambi_glass_hum, rate: 0.4 , release: 0.5, start: 0.1, finish: 0.16, amp: 0.5, pan: 1
  play 30, amp: 0.05, attack: 0.4, release: 0.2
  sample :drum_bass_soft, amp: 0.1
  sleep 0.1
  with_fx :flanger, depth:18 do
    sample :ambi_glass_hum, rate: 4, release: 0.4, amp: 0.1
  end
  
end



killing