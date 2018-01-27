define :winning do
  start_tone = 70
  with_fx :lpf do
    with_fx :reverb do
      play start_tone, release: 0.3, pan:rrand(-1,0), amp:0.1
      sleep 0.03
      play start_tone+3, release: 0.3, pan:rrand(-0.5,0.5), amp:0.2
      sleep 0.03
      play start_tone+5, release: 0.3, pan:rrand(0,1), amp:0.2, amp:0.3
      sleep 0.03
      play start_tone+7, release: 0.6, pan:rrand(-1,1), amp:0.4
      sleep 0.03
      play start_tone+12, release: 0.6, amp:1
    end
  end
end

live_loop :abmi_glass do
  
  sample :ambi_glass_hum, start: 0.2 , amp: 0.1
  sample :elec_ping, rate: 2
  sample :bd_808, amp: 1
  sleep 1
end

live_loop :penta do
  play_pattern_timed chord(:c5, :M), [0.5]
  sleep 1
  play_pattern_timed chord(:d5, :M), [0.5]
  sleep 1
  play_pattern_timed chord(:e5, :M), [0.5]
  sleep 1
  play_pattern_timed chord(:f5, :M), [0.5]
  sleep 1
end

comment do
  use_synth :dtri
  with_fx :lpf do
    with_fx :reverb do
      play_pattern_timed [:c5, :d5, :b5, :c6],[0.5, 1, 0.5, 1]
      
      
    end
  end
end



